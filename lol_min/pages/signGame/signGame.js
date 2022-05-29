import { request } from "../../requst/index.js";
import regeneratorRuntime from "../../lib/runtime/runtime";

Page({
  /**
   * 页面的初始数据
   */
  data: {
    isplay: true,
    info: {
      name: "", //名字
      id: "", //学号
      phone: "", //电话
    },
  },
  //修改信息

  //是否报名
  async isplay() {
    const res = await request({ url: "/isapply" });
    this.setData({
      isplay: res.data.data,
    });
  },
  async subinfo() {
    const myinfo = this.data.info;
    if (myinfo.id.length != 10 && myinfo.phone.length != 11) {
      wx.showToast({
        title: "您的信息位数不对噢",
        image: "../../img/sign_fail.png",
      });
    } else {
      const res = await request({
        url: "/apply",
        method: "POST",
        data: { name: myinfo.name, id: myinfo.id, phone: myinfo.phone },
      });
      console.log(res);
      if (res.data.code != 200) {
        wx.showToast({
          title: res.data.message,
          image: "../../img/sign_fail.png",
        });
      } else {
        wx.showModal({
          title: "是否去创建我的战队",
          content: "战队只需要队长一个人填写",
          showCancel: true,
          cancelText: "取消",
          cancelColor: "#000000",
          confirmText: "确定",
          confirmColor: "#3CC51F",
          success: (result) => {
            if (result.confirm) {
              wx.navigateTo({
                url: "/pages/myTeam/myTeam",
              });
              this.isplay();
            } else {
              this.isplay();
            }
          },
        });
      }
    }
  },
  inputInfo(e) {
    // 1. input 和 info 双向数据绑定
    const dataset = e.currentTarget.dataset;
    //data-开头的是自定义属性，可以通过dataset获取到，dataset是一个json对象，有obj和item属性，可以通过这两个实现双向数据绑定，通过更改这两个值，对不同name的变量赋值
    const value = e.detail.value;
    this.data[dataset.obj][dataset.item] = value;
    this.setData({
      info: this.data[dataset.obj],
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.isplay();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {},

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {},
});
