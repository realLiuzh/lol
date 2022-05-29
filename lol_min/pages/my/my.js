import {request} from"../../requst/index.js";
import regeneratorRuntime from"../../lib/runtime/runtime";


Page({

  /**
   * 页面的初始数据
   */
  data: {
    point:'',
    imgtext:"登录",//
    showMy:false,//遮罩层显示
    userInfo:{},//用户信息
  },
  
  //后台绑定数据
  async putinfor(wxName){
    await request({url:'/bind',method:"POST",data:{wxName}});
    
  },
  //获取积分
  async getpoint(){
    const res =await request({url:'/credits'});
    console.log(res)
    this.setData({
      point:res.data.data
    })
  },
  //获取用户信息
  getuser(){
    wx.getUserProfile({
      desc:'用于完善信息',
      success:(res) =>{
        const {userInfo} = res
        wx.setStorage({
          key: 'userInfo',
          data: userInfo,
        })
        this.setData({
          userInfo,
          showMy:true
        })
        this.putinfor(userInfo.nickName)
      },
      fail:(res) =>{
        wx.showModal({
          title: '提示',
          content: '请先登录才能看到信息噢',
          showCancel: true,
          confirmColor: '#3CC51F',
          })
      }
    })
  },
  sub(){
    this.getuser()
  },


  /**
   * 生命周期函数--监听页面加载
   */

  onLoad: function (options) {
    
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.getpoint()
    // const userInfo = wx.getStorageSync('userInfo');
    // if(userInfo != {}){
    //   this.setData({
    //     showMy:true
    //   })
    //    this.setData({
    //      userInfo
    //    })
    // }else{
    //    this.setData({
    //      showMy:false
    //    })
    // }
  
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