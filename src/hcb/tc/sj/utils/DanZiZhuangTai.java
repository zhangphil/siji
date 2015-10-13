package hcb.tc.sj.utils;

public enum DanZiZhuangTai {

	QIANGDAN(-10, "抢单"), JINGJIA(-11, "竞价"), YIJINGJIA(-12, "已竞价");

	public int type;
	public String name;

	private DanZiZhuangTai(int type, String name) {
		this.name = name;
		this.type=type;
	}
	
	public	static	String	getName(int type){
		String str = "未知";

		for (DanZiZhuangTai d : DanZiZhuangTai.values()) {
			if (d.type == type) {
				str = d.name;
			}
		}

		return str;
	}
	
}
