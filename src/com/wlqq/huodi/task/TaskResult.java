package com.wlqq.huodi.task;


import com.wlqq.huodi.exception.ErrorCode;

/**
 * @author Tiger Tang
 *         Date: 12-1-19
 *         Time: 上午10:13
 * @since 0.1.20
 */
public class TaskResult<T> {

	private Status status;
	private ErrorCode errorCode;
	private T content;

	public TaskResult(Status status) {
		this.status = status;
	}

	public TaskResult(Status status, ErrorCode errorCode) {
		this.status = status;
		this.errorCode = errorCode;
	}

	public TaskResult(Status status, T content) {
		this.status = status;
		this.content = content;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public static enum Status {
		OK, ERROR, IO_ERROR, JSON_ERROR, INTERNAL_ERROR, UNKNOWN_ERROR, DNS_ERROR
	}

	@Override
	public String toString() {
		return "TaskResult{" +
				"status=" + status +
				", errorCode=" + errorCode +
				", content=" + content +
				'}';
	}
}
