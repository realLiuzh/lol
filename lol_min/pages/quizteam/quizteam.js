import {request} from"../../requst/index.js";
import regeneratorRuntime from"../../lib/runtime/runtime";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    point:'',//剩余积分
    id:'',//传过来的id
    teamId:'',
    score:'',
    teamInform: {
      "id": 1,
      "teamId1": 6,
      "teamName1": "XYZ",
      "teamId2": 7,
      "teamName2": "HZBS",
      "createDate": "2021-08-27"
  }
  },
  //获取我的剩余积分
  async getpoint(){
    const res =await request({url:'/credits'});
    console.log(res)
    this.setData({
      point:res.data.data
    })
  },
  //提交投注
  async sub(){
    const res =await request({url:'/bet',method: "POST",data: { competitionId:this.data.teamInform.id,betTeamId:this.data.teamId,betScore:this.data.score }});
    if(res.data.code = 200){
      wx.showToast({
        title: res.data.message,
        icon: 'none',
        image: '../../img/fox.png',
        duration: 1500,
        mask: false,
      });
        
    }
  },
  //积分双线绑定
  pointPut(e){
    let that = this
    //data-开头的是自定义属性，可以通过dataset获取到，dataset是一个json对象，有obj和item属性，可以通过这两个实现双向数据绑定，通过更改这两个值，对不同name的变量赋值
    const value = e.detail.value;
    that.setData({
      score: value
    });
  },
  //获取该队伍的比赛信息
  async getteamInform(){
    const res =await request({url:'/competition',data: { id:this.data.id}});
    this.setData({
      teamInform:res.data.data
    })
  },
  intteamone(){
    wx.showToast({
      title: '你已经选择'+this.data.teamInform.teamName1,
      image: '../../img/fox.png',
      duration: 1500,
      mask: false,
    });
    this.setData({
      teamId:this.data.teamInform.teamId1
    })
  },
  intteamtwo(){
    wx.showToast({
      title: '你已经选择'+this.data.teamInform.teamName2,
      image: '../../img/fox.png',
      duration: 1500,
      mask: false,
    });
    this.setData({
      teamId:this.data.teamInform.teamId2
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getpoint()
    this.setData({
      id:options.id
    })
    this.getteamInform()
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