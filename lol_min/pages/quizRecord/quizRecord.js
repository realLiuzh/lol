import {request} from"../../requst/index.js";
import regeneratorRuntime from"../../lib/runtime/runtime";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    team:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
   async getRecord(){
    const res =await request({url:'/mybet'});
    console.log(res)
    this.setData({
      team:res.data.data
    })
  },
  onLoad: function (options) {
    this.getRecord()
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