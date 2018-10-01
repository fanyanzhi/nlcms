/**
 *  Copyright (c) 2015 Neusoft.com corporation All Rights Reserved.
 */

package cn.gov.nlc.aleph;

import java.util.ArrayList;
import java.util.List;

/**
 *  此处进行功能描述。
 *
 *  @author Hp
 *  @version 1.0
 *
 *  <pre>
 *  使用范例：
 *  创建时间:2015-8-21 下午01:40:01
 *  修改历史：
 *     ver     修改日期     修改人  修改内容
 * ──────────────────────────────────
 *   V1.00   2015-8-21   Hp  初版
 *
 *  </pre>
 *
 */
public class PresentRecord{
	//题名
	private String timing;
	//主题
	private String zhuti;
	//内容提要
	private String neirongtiyao;
	//中图分类号
	private String zhongtufenleihao;
	//isbn
	private String isbn;
	
	public String doc_number="";
	public String base="";
	//著者
	public String author="";
	//题名
	public String title="";
	//资料类型
	public String type="";

	//出版社
	public String publishHouse="";

	//出版年
	public String publishYear="";
	//馆藏地
	public List<String> collectLocList=new ArrayList<String>();
	
	@Override
	public String toString() {
		return doc_number+", "+base+", "+author+", "+title+", "+type+", "+publishYear+", ";
	}
	public String getDoc_number() {
		return doc_number;
	}
	public void setDoc_number(String doc_number) {
		this.doc_number = doc_number;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPublishHouse() {
		return publishHouse;
	}
	public void setPublishHouse(String publishHouse) {
		this.publishHouse = publishHouse;
	}
	public String getPublishYear() {
		return publishYear;
	}
	public void setPublishYear(String publishYear) {
		this.publishYear = publishYear;
	}
	public List<String> getCollectLocList() {
		return collectLocList;
	}
	public void setCollectLocList(List<String> collectLocList) {
		this.collectLocList = collectLocList;
	}
	public String getTiming() {
		return timing;
	}
	public void setTiming(String timing) {
		this.timing = timing;
	}
	public String getZhuti() {
		return zhuti;
	}
	public void setZhuti(String zhuti) {
		this.zhuti = zhuti;
	}
	public String getNeirongtiyao() {
		return neirongtiyao;
	}
	public void setNeirongtiyao(String neirongtiyao) {
		this.neirongtiyao = neirongtiyao;
	}
	public String getZhongtufenleihao() {
		return zhongtufenleihao;
	}
	public void setZhongtufenleihao(String zhongtufenleihao) {
		this.zhongtufenleihao = zhongtufenleihao;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
}

