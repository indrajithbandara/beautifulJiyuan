package lzh.ys.beans;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class Scenic_Detail_Bean {
	private String name;
	private int next;
	private String description;
	private Bitmap url_t;
	private String mUrl;
	private List<String> url_list = new ArrayList<String>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the next
	 */
	public int getNext() {
		return next;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(int next) {
		this.next = next;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the url_t
	 */
	public Bitmap getUrl_t() {
		return url_t;
	}

	/**
	 * @param url_t
	 *            the url_t to set
	 */
	public void setUrl_t(Bitmap url_t) {
		this.url_t = url_t;
	}

	/**
	 * @return the url_list
	 */
	public List<String> getUrl_list() {
		return url_list;
	}

	/**
	 * @param url_list
	 *            the url_list to set
	 */
	public void setUrl_list(List<String> url_list) {
		this.url_list = url_list;
	}

	/**
	 * @return the mUrl
	 */
	public String getmUrl() {
		return mUrl;
	}

	/**
	 * @param mUrl
	 *            the mUrl to set
	 */
	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}

}
