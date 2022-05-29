import {request} from"../../requst/index.js";
import regeneratorRuntime from"../../lib/runtime/runtime";

const myaudio = wx.createInnerAudioContext();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgsrc:'',
    //积分总榜
    listPoint:[],
    //每日时间排名数组
    listTime:[],
    tabs:[
      {
        id:0,
        name:"每日签到前三",
        isActive:true,
      },{
        id:1,
        name:"积分前十排名",
        isActive:false,
      }
    ],
    showpage:true,
  },
  async sub (){
    //防抖
    clearTimeout(this.timeId);
       this.timeId = setTimeout(() =>{
         this.subinfo();
       },1000)
  },
  async subinfo(){
      const res = await request({url:'/sign',method:"POST"});
      console.log(res)
      //写信息反馈
      // if(res.data.code = 200){
        wx.showModal({
         title: "签到",
         content: res.data.message,
         showCancel: true,
         cancelText: '取消',
         cancelColor: '#000000',
         confirmText: '确定',
         confirmColor: '#3CC51F',
       });
      // }else{
      //   wx.showModal({
      //     title: res.data.message,
      //     content: '峡谷召唤师，明天记得来噢',
      //     showCancel: true,
      //     cancelText: '取消',
      //     cancelColor: '#000000',
      //     confirmText: '确定',
      //     confirmColor: '#3CC51F',
      //   });
      // }
       
        
      //  if(res.data.code = 200){
      //    wx.showToast({
      //      title: '您是第'+res.data.data.continuousSignCount+'名',
      //      icon: '../../img/fox.png',
      //      duration: 1500,
      //      mask: false,
      //    });
      //  }else{
      //    wx.showToast({
      //      title: res.data.message,
      //      image: '../../img/sign_fail.png',
      //      duration: 1500,
      //      mask: false,
      //    });
      //  }
    this.listTime()
    this.listPoint()
  },
  async listTime(){
    const res = await request({url:'/sign/rank'});
    const listTime = res.data.data
    this.setData({
      listTime
    })
  },
  async listPoint(){
    const res = await request({url:'/credits/rank'});
    console.log(res)
    const listPoint = res.data.data
    this.setData({
      listPoint
    })
  },

  //tab组件
   handleItemChange(e){
    const {index} = e.detail;
     //获取data数组
      let {tabs} = this.data;
      //循环数组
      tabs.forEach((V,i) =>i === index?V.isActive=true:V.isActive=false);
      this.setData({
        tabs
      })
      if(tabs[1].isActive === true){
        this.setData({
          showpage:false
        })
      }else{
        this.setData({
          showpage:true
        })
      }
  },
  onLoad: function (options) {
    this.listTime()
    this.listPoint()
    myaudio.src = 'http://47.96.86.132:9099/4.mp3'
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    myaudio.play();
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