package entity;

import javafx.collections.ObservableList;

public class GroupEntity {
	private int groupId;
	private String groupName;
	private int leaderId;
	private ObservableList<UserEntity> memberList;
	
	
	public GroupEntity(int groupId, String groupName, int leaderId, ObservableList<UserEntity> memberList) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.leaderId = leaderId;
		this.memberList = memberList;
	}
	
	
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}



	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}
	public ObservableList<UserEntity> getMemberList() {
		return memberList;
	}
	public void setMemberList(ObservableList<UserEntity> memberList) {
		this.memberList = memberList;
	}
	
}
