package com.heishuidi.common;
/**Э����
 * �����װ ��������ͻ����໥�������Ϣ
 * ��ʲô���͵İ����������Ͳ�ͬ������Ϣ��������ͬ�Ĵ���
 * 		1:��½�ɹ���
 * 		2:��¼ʧ�ܰ�
 * 		3:��ͨ��Ϣ��
 * 		4:��ȡ���ߺ��Ѱ�
 * 		5:�������ߺ��Ѱ�
 * ˭���͵�
 * ���͸�˭
 * ��Ϣ����
 * ����˭
 * */
public class Message  implements java.io.Serializable{
	private String mesType;//��������
	private String sender;//������
	private String getter;//������
	private String con;//��Ϣ����
	private String sendTime;//����ʱ��
	public String getMesType() {
		return mesType;
	}
	public void setMesType(String mesType) {
		this.mesType = mesType;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getGetter() {
		return getter;
	}
	public void setGetter(String getter) {
		this.getter = getter;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
