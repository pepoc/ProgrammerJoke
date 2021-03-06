package com.pepoc.programmerjoke.log;

public interface Log {
	
	public void v(String message, Throwable t);
	
	public void v(String message);

	public void d(String message, Throwable t);
	
	public void d(String message);
	
	public void i(String message, Throwable t);
	
	public void i(String message);
	
	public void w(String message, Throwable t);
	
	public void w(String message);
	
	public void e(String message, Throwable t);
	
	public void e(String message);
}
