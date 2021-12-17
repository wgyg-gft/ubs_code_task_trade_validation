package com.gft.codetest.util;

public class ServiceResponse<T> {

	boolean isSuccess;

	String remark;
	Exception exception;

	public T payload;

	public static <T> ServiceResponse<T> ok(T payload) {
		ServiceResponse<T> sr = new ServiceResponse<>();
		sr.setSuccess(true);
		sr.setPayload(payload);

		return sr;
	}

	public static <T> ServiceResponse<T> ok(T payload, String remark) {
		ServiceResponse<T> sr = ok(payload);
		sr.setRemark(remark);
		return sr;
	}

	public static <T> ServiceResponse<T> error(String remark) {
		ServiceResponse<T> sr = new ServiceResponse<>();
		sr.setSuccess(false);
		sr.setRemark(remark);

		return sr;
	}

	public static <T> ServiceResponse<T> error(Exception exception, String remark) {
		ServiceResponse<T> sr = new ServiceResponse<>();
		sr.setSuccess(false);
		sr.setException(exception);
		sr.setRemark(remark);

		return sr;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
