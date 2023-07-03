package entity;

public class UserSingleton {
	
	private static UserEntity instance;
	
	private UserSingleton() {
		instance = new UserEntity();
	}
	
	public static final UserEntity getInstance() {
		return instance;
	}
	
	public static final void setInstance(UserEntity user) {
		instance = user;
	}

}
