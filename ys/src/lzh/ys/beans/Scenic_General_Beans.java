package lzh.ys.beans;

import java.util.ArrayList;
import java.util.List;

public class Scenic_General_Beans {
	private String address;
	private String phone;
	private String price;
	private ArrayList<String> Img_lists=new ArrayList<String>();

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the img_lists
	 */
	public ArrayList<String> getImg_lists() {
		return Img_lists;
	}

	/**
	 * @param img_lists
	 *            the img_lists to set
	 */
	public void setImg_lists(ArrayList<String> img_lists) {
		Img_lists = img_lists;
	}

}
