package vo;

public class SalesByFilmCategory {
	private String category;
	private long totalSales;
	@Override
	public String toString() {
		return "SalesByFilmCategory [category=" + category + ", totalSales=" + totalSales +  "]";
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public long getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(long totalSales) {
		this.totalSales = totalSales;
	}
	
	
}
