package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.GroupEntity;
import entity.FoodEntity;
import entity.UserEntity;
import entity.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.SqliteConnection;

public final class GroupController {

	public static String getGroupNameById(int groupId) {

		String query = "select group_name from groups where group_id =" + String.valueOf(groupId) + ";";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement()) {

			ResultSet rs = sttm.executeQuery(query);

			if (rs.next()) {
				return rs.getString("group_name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int getGroupIdByNameGroup(String nameGroup) {

		String query = "select group_id from groups where group_name='" + nameGroup + "';";

		int groupId = 0;
		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(query);

			rs.next();

			groupId = rs.getInt("group_id");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return groupId;
	}
	
	public static GroupEntity getGroupOfUserByGroupId(int groupId) {

		GroupEntity group = null;

		String groupMemberQuery = "select u.user_id, u.username from groups as gr, group_member as gm, users as u where gr.group_id = gm.group_id and "
				+ "gm.user_id = u.user_id and " + "gr.group_id=" + String.valueOf(groupId) + ";";

		String leaderIdQuery = "select leader_id, group_name from groups where group_id=" + String.valueOf(groupId)
				+ ";";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet leader = sttm.executeQuery(leaderIdQuery);

			leader.next();
			int leaderId = leader.getInt("leader_id");
			String nameGroup = leader.getString("group_name");

			leader.close();

			ObservableList<UserEntity> userList = FXCollections.observableArrayList();

			ResultSet rs = sttm.executeQuery(groupMemberQuery);

			while (rs.next()) {

				if (rs.getInt("user_id") == leaderId) {
					userList.add(new UserEntity(rs.getInt("user_id"), rs.getString("username"), "Trưởng nhóm"));
				} else {
					userList.add(new UserEntity(rs.getInt("user_id"), rs.getString("username"), "Thành viên"));

				}

			}

			group = new GroupEntity(groupId, nameGroup, leaderId, userList);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return group;
	}

	public static int createGroup(String leaderId, String groupName) {

		int groupId = 0;
		String groupQuery = "insert into groups (leader_id, group_name) values (" + leaderId + ", '" + groupName
				+ "');";
		String groupIdQuery = "select group_id from groups where group_name ='" + groupName + "';";

		ResultSet rs;
		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement()) {

			ResultSet idExists = sttm.executeQuery(groupIdQuery);
			if (!idExists.next()) {
				sttm.execute(groupQuery);

				rs = sttm.executeQuery(groupIdQuery);
				rs.next();
				groupId = rs.getInt("group_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return groupId;
	}

	public static boolean deleteGroup(int groupId) {

	
		String groupName = getGroupNameById(groupId);
		
		if (groupName != null) {
			ObservableList<FoodEntity> foodList = getFoodInGroup(groupName);

			for (FoodEntity food : foodList) {
				deleteFoodInGroup(food.getRaw_food_id(), groupId);
			}
			

			ArrayList<Integer> memberList = getMemberIdByGroupId(groupId);
			
			for (Integer id : memberList) {
				deleteGroupMember(id.intValue(), groupId);
			}
			
			String query = "delete from groups where group_id = ?;";

			try (Connection conn = SqliteConnection.Connector(); 
				 PreparedStatement psttm = conn.prepareStatement(query)) {

				psttm.setInt(1, groupId);
				psttm.execute();
				
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return false;
	}

	public static void updateGroup(String leaderId, String groupName) {
		Connection conn = SqliteConnection.Connector();

		String groupQuery = "update groups " + "set leader_id=" + String.valueOf(leaderId) + ", " + "group_name = '"
				+ groupName + "';";
		Statement sttm;

		try {
			conn.setAutoCommit(false);
			sttm = conn.createStatement();

			sttm.execute(groupQuery);

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
		}

		try {

			conn.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static int createGroupMember(String groupName, ObservableList<UserEntity> memberList, int leaderId) {

		String insert = "insert into group_member (group_id, user_id) values (?, ?)";

		try (Connection conn = SqliteConnection.Connector(); PreparedStatement psttm = conn.prepareStatement(insert)) {

			int groupId = createGroup(String.valueOf(leaderId), groupName);

			if (groupId == 0) {
				return 0;
			}

			psttm.setInt(1, groupId);
			psttm.setInt(2, leaderId);
			psttm.execute();

			if (!memberList.isEmpty()) {
				for (UserEntity user : memberList) {
					psttm.setInt(1, groupId);
					psttm.setInt(2, user.getUser_id());
					psttm.execute();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
		return 1;
	}

	public static ArrayList<Integer> getMemberIdByGroupId (int groupId) {
		
		ArrayList<Integer> memberList = new ArrayList<Integer>();
		
		String query = "select user_id from group_member where group_id =" + String.valueOf(groupId) + ";";

		try (Connection conn = SqliteConnection.Connector(); 
			 Statement sttm = conn.createStatement()) {

			ResultSet rs = sttm.executeQuery(query);

			while (rs.next()) {
				memberList.add(rs.getInt("user_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return memberList;
	}
	
	public static boolean addGroupMember(int groupId, int userId) {
		String insertQuery = "INSERT INTO group_member (group_id, user_id) VALUES (?, ?)";
		try (Connection conn = SqliteConnection.Connector();
				PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

			pstmt.setInt(1, groupId);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteGroupMember(int userId, int groupId) {

		Connection conn = SqliteConnection.Connector();

		String deleteUser = "delete from group_member where user_id=" + String.valueOf(userId) + " and group_id="
				+ String.valueOf(groupId) + ";";
		try (Statement sttm = conn.createStatement();) {

			conn.setAutoCommit(false);

			sttm.execute(deleteUser);

			sttm.close();
			conn.commit();
			return true;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				e.printStackTrace();
			}
		}

		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static ObservableList<String> getGroupNameOfUser() {

		UserEntity userLogin = UserSingleton.getInstance();

		String query = "select gr.group_name from groups as gr, group_member as gm, users as u where gr.group_id = gm.group_id and "
				+ "gm.user_id = u.user_id and " + "u.user_id=" + String.valueOf(userLogin.getUser_id()) + ";";

		ObservableList<String> li = FXCollections.observableArrayList();

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(query);

			while (rs.next()) {
				li.add(rs.getString("group_name"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return li;
	}

	public static UserEntity getUserByUsername(String username) {

		String query = "select user_id, username from users where username = " + "'" + username + "'";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(query);

			if (rs.next()) {
				return new UserEntity(rs.getInt("user_id"), rs.getString("username"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	public static FoodEntity getFoodByName(String nameFood) {

		String query = "select * from raw_foods where raw_food_name= '" + nameFood + "' collate nocase;";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(query);

			if (rs.next()) {
				return new FoodEntity(rs.getInt("raw_food_id"), rs.getString("raw_food_name"), rs.getInt("food_type"),
						rs.getString("unit"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	public static ObservableList<FoodEntity> getFoodInGroup(String nameGroup) {

		ObservableList<FoodEntity> foodList = FXCollections.observableArrayList();

		String foodListQuery = "select rf.raw_food_id, rf.raw_food_name, rf.food_type, rf.unit, gsl.number from groups as gr, group_shopping_list as gsl, raw_foods as rf where gr.group_id = gsl.group_id and gsl.food_id = rf.raw_food_id and group_name ='"
				+ nameGroup + "';";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(foodListQuery);

			while (rs.next()) {
				foodList.add(new FoodEntity(rs.getInt("raw_food_id"), rs.getString("raw_food_name"),
						rs.getInt("food_type"), rs.getInt("number"), rs.getString("unit")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foodList;
	}

	public static boolean insertFoodIntoGroup(int foodId, int groupId, int soLuong) {

		String foodExists = "select * from group_shopping_list where group_id = ? and food_id = ?";

		String query = "insert into group_shopping_list (group_id, food_id, number) values (?, ?, ?);";

		try (Connection conn = SqliteConnection.Connector();
				PreparedStatement psttm = conn.prepareStatement(foodExists);) {
			psttm.setInt(1, groupId);
			psttm.setInt(2, foodId);
			ResultSet rs = psttm.executeQuery();

			if (rs.next()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (Connection conn = SqliteConnection.Connector(); PreparedStatement psttm = conn.prepareStatement(query);) {
			psttm.setInt(1, groupId);
			psttm.setInt(2, foodId);
			psttm.setInt(3, soLuong);
			psttm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public static void updateFoodInGroup(int foodId, int groupId, int soLuong) {

		String query = "update group_shopping_list set number = ? where food_id = ? and group_id = ?;";

		try (Connection conn = SqliteConnection.Connector(); PreparedStatement psttm = conn.prepareStatement(query);) {

			psttm.setInt(1, soLuong);
			psttm.setInt(2, foodId);
			psttm.setInt(3, groupId);

			psttm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean deleteFoodInGroup(int foodId, int groupId) {

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			sttm.executeUpdate("delete from group_shopping_list where group_id=" + String.valueOf(groupId)
					+ " and food_id=" + String.valueOf(foodId) + ";");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}