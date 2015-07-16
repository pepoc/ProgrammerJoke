package com.pepoc.programmerjoke.log;

public class LogImpl implements Log {
	
	private String name;
	
	public LogImpl(String name) {
		this.name = name;
	}

	@Override
	public void v(String message, Throwable t) {
		if (LogFactory.isDebugEnable) {
			android.util.Log.v(name, message, t);
		}
	}

	@Override
	public void v(String message) {
		if (LogFactory.isDebugEnable) {
			android.util.Log.v(name, message);
		}
	}

	@Override
	public void d(String message, Throwable t) {
		if (LogFactory.isDebugEnable) {
			android.util.Log.d(name, message, t);
		}
	}

	@Override
	public void d(String message) {
		if (LogFactory.isDebugEnable) {
			android.util.Log.d(name, message);
		}
	}

	@Override
	public void i(String message, Throwable t) {
		if (LogFactory.isDebugEnable) {
			android.util.Log.i(name, message, t);
		}
	}

	@Override
	public void i(String message) {
		if (LogFactory.isDebugEnable) {
			android.util.Log.i(name, message);
		}
	}

	@Override
	public void w(String message, Throwable t) {
		if (LogFactory.isDebugEnable) {
			android.util.Log.w(name, message, t);
		}
	}

	@Override
	public void w(String message) {
		if (LogFactory.isDebugEnable) {
			android.util.Log.w(name, message);
		}
	}

	@Override
	public void e(String message, Throwable t) {
		if (LogFactory.isDebugEnable) {
			android.util.Log.e(name, message, t);
		}
	}

	@Override
	public void e(String message) {
		if (LogFactory.isDebugEnable) {
			android.util.Log.e(name, message);
		}
	}

}
