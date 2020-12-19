# 数位板的识别
**主要作用:**
    学校人机交互实验室研究生和博士的师哥师姐，目前实验数据来源比较少，缺乏一个方便快捷的应用帮助他们获取实验数据，以便于研究和做实验并发表论文。

**环境**
    32位的1.6版本的jdk就行，因为数位板驱动也是32位的，更高版本的jdk我没有试过，应该会向下兼容吧。

**数位板jar包和api**
- [jTablet的jar包](http://jtablet.cellosoft.com/develop.html)
- [api](http://jtablet.cellosoft.com/jtablet2/docs/api/index.html)
- [jar包源码](https://github.com/marcello3d/jtablet)

## 2020-12-07
这几天基本功能已经基本完成，已经能够正常获取手写笔的参数并且写入到csv文件中。
## 2020-12-10
坐标变换，完成基本界面，导出的数据csv数据生成的散点图，坐标系为右下，视图正常。
![764ffee0525af8fa4413aecac47125c-2020-12-10-20-53-26](https://picgo-1259138584.cos.ap-beijing.myqcloud.com/Markdown/764ffee0525af8fa4413aecac47125c-2020-12-10-20-53-26.png)
## 2020-12-19
通过三次样条插值得到的平滑的曲线曲线，可用于求解速度（一阶导数）和加速度（二阶导数）。
自变量时间，因变量x轴或者y轴方向的位移。
**下图为三次样条插值得到的函数曲线** 测试数据正常
![20201219141629-2020-12-19-14-16-29](https://picgo-1259138584.cos.ap-beijing.myqcloud.com/Markdown/20201219141629-2020-12-19-14-16-29.png)
