package com.example.hongaer.shoppingmall2.shoppingcart.bean;

import java.io.Serializable;

/**
 * 店铺信息
 */
public class StoreBean implements Serializable
{
	protected String Id;
	protected String name;
	protected boolean isChoosed;
    private boolean isEdtor;

	public boolean isEdtor() {
		return isEdtor;
	}

	public void setIsEdtor(boolean isEdtor) {
		this.isEdtor = isEdtor;
	}

	public StoreBean(String name) {
		this.name = name;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public  String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChoosed() {
		return isChoosed;
	}

	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}

	@Override
	public String toString() {
		return "StoreBean{" +
				"Id='" + Id + '\'' +
				", name='" + name + '\'' +
				", isChoosed=" + isChoosed +
				", isEdtor=" + isEdtor +
				'}';
	}
}
