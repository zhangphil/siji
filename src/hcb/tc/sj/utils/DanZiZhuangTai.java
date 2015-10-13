package hcb.tc.sj.utils;

public enum DanZiZhuangTai {

	QIANGDAN(-10, "抢单"), JINGJIA(-11, "竞价"), YIJINGJIA(-12, "已竞价");

	public int type;
	public String name;

	private DanZiZhuangTai(int type, String name) {
		this.name = name;
	}
}
