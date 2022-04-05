package vo;

public class Staff {
	private int staffId;		// 스태프아이디
	private String firstame;	// 첫번쨰이름
	private String lastName;	// 마지막이름
	private int addressId;		// 주소아이디
	private String email;		// 이메일
	private int storeId;		// 스토어아이디
	private int active;			// 활동
	private String username;	// 사용자이름
	private String password;	// 패스워드
	private String lastUpdate;	// 마지막수정시간
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public String getFirstame() {
		return firstame;
	}
	public void setFirstame(String firstame) {
		this.firstame = firstame;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
}
