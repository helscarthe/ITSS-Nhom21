package entity;

public class UserEntity {
	
	private int user_id;
	private String username;
	private String password_hash;
	private boolean is_admin;
	
	public UserEntity() {
	}
	
	public UserEntity(int user_id, String username, String password_hash, boolean is_admin) {
		this.user_id = user_id;
		this.username = username;
		this.password_hash = password_hash;
		this.is_admin = is_admin;
	}

	public int getUser_id() {
		return user_id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword_hash() {
		return password_hash;
	}
	
	public boolean isAdmin() {
		return is_admin;
	}
}
