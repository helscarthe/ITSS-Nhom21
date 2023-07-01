package entity;

public class UserEntity {
		private int user_id;
		private String username;
		private String password_hash;
		private boolean is_admin;
		
		
		
		public UserEntity(int user_id, String username, String password_hash, boolean is_admin) {
			this.user_id = user_id;
			this.username = username;
			this.password_hash = password_hash;
			this.is_admin = is_admin;
		}
		
		public UserEntity(int user_id, String username) {
			this.user_id = user_id;
			this.username = username;
		}



		public int getUser_id() {
			return user_id;
		}
		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword_hash() {
			return password_hash;
		}
		public void setPassword_hash(String password_hash) {
			this.password_hash = password_hash;
		}
		public boolean isIs_admin() {
			return is_admin;
		}
		public void setIs_admin(boolean is_admin) {
			this.is_admin = is_admin;
		}
		
		
	
}
