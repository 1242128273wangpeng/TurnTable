# TurnTable
#### 客户和服务者之间的转盘互动游戏
绘制转盘的思路：canves的旋转，所有的内容都直接画在转盘的中间，转盘每个区域就是通过旋转canves的操作，先画转盘的选项区域，旋转的角度为当前的角度和下一个角度之和的一半。`canves.save`后再画转盘项上的文字和图片，排版换行服务端在文字中插入"\n"，先测量文字`paint.measureText`或者图片`bitmap.width`，起始位置就为`(width-content_width)/2`，bitmap需要`Bitmap.createScaledBitmap`按比例进行缩放之后再绘制到对应的位置，中途可能需要canves.translate移动画布的起始点，绘制完之后再次重置起点，之后再旋转和绘制选项区域类似,所有绘制完成再` canvas.restore()`。  <br /><br />
旋转转盘的思路：RotateAnimation动画，旋转角度和上面类似但是当前的角度加上下一个角度之和的一半，依次累加到结果项，这累加的角度是顺时针的，所以结果需要用360减去累加的角度。并且加了AccelerateDecelerateInterpolator，这个差值器是开始和结束都减速，中间过程加速。 <br />

绘制由于在onDraw方法中，为了保证帧率保证流畅度，尽量在之前就创建好对象分配好内存，不要在该方法中执行。 <br /> <br />
图片说明：
<div align=center><img width="540" height="360" src="https://github.com/1242128273wangpeng/TurnTable/blob/master/image/desc_turntable.png "/></div>

动画效果： <br /> <br />
<div align=center><img src="https://github.com/1242128273wangpeng/TurnTable/blob/master/gif/turntable.gif"/></div>





