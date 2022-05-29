import {request} from"../../requst/index.js";
import regeneratorRuntime from"../../lib/runtime/runtime";


  
const myaudio = wx.createInnerAudioContext();
Page({

  /**
   * 页面的初始数据
   */
   data: {
    imgUrl:'',
    src: 'http://47.96.86.132:9099/1.mp3',
    code:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  //获取图片
  async getImage(){
    const res = await request({url:'/random/file',});
    console.log(res)
    const {photo} = res.data.data
    this.setData({
      imgUrl:photo
    })
    
  },
  async getOpenid(code){
    const res = await request({url:'/openid',method:"POST",data:{code}});
    const {data} = res.data
    wx.setStorage({
      key: 'openid',
      data: data,
    });
      
  },
  //点击后暂停音乐
  next(){
    myaudio.pause();
  },
  onLoad: function (options) {
    wx.login({
      success: (result) => {
          this.getOpenid(result.code)
      },
    });
    myaudio.src = this.data.src
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  
  onReady: function () {
    this.getImage();
    myaudio.play();
      myaudio.onEnded((res)=>{
       setTimeout(() => {
        wx.switchTab({
          url: '/pages/signIn/signIn'
        })
       }, 2000);
     })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})