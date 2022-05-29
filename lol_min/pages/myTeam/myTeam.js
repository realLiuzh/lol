import { request } from "../../requst/index.js";
import regeneratorRuntime from "../../lib/runtime/runtime";
Page({
  /**
   * 页面的初始数据
   */
  data: {
    showBut: false,
    team: {
      id: "", //是否创建的id
      teamName: "",
      captainId: "",
      memberId: ["", "", "", "", "", ""],
    },
  },
  //双向绑定
  subteam(e) {
    let that = this
    const {dataset} = e.currentTarget;
    //data-开头的是自定义属性，可以通过dataset获取到，dataset是一个json对象，有obj和item属性，可以通过这两个实现双向数据绑定，通过更改这两个值，对不同name的变量赋值
    const value = e.detail.value;
    const nameobj = dataset.obj
    
    let name =  'team.' + nameobj
    that.setData({
      [name]: value
    });
    console.log(this.data.team)
  },
  async exit() {
    const res = await request({
      url: "/team/exit",
    });
    if (res.data.code == 200) {
      wx.showToast({
        title: "您已退出",
        image: "../../img/fox.png",
        duration: 1500,
      });
    }
  },
  async isexit() {
    wx.showModal({
      title: "退出指令",
      content: "请确认是否退出",
      showCancel: true,
      cancelText: "取消",
      cancelColor: "#000000",
      confirmText: "确定",
      confirmColor: "#3CC51F",
      success: (result) => {
        if (result.confirm) {
          this.exit();
        }
      },
    });
  },

  async changeinfo() {
    const res = await request({
      url: "/team/build",
      method: "POST",
      data: { team: this.data.team },
    });
    console.log(res);
    if (res.data.code != 200) {
      wx.showToast({
        title: res.data.message,
        image: "../../img/sign_fail.png",
      });
    } else {
      wx.showToast({
        title: res.data.message,
        image: "../../img/fox.png",
      });
    }
  },
  async subinfo() {
    const res = await request({
      url: "/team/build",
      method: "POST",
      data: { team: this.data.team },
    });
    console.log(res);
    if (res.data.code != 200) {
      wx.showToast({
        title: res.data.message,
        image: "../../img/sign_fail.png",
      });
    } else {
      wx.showToast({
        title: res.data.message,
        image: "../../img/fox.png",
      });
    }
  },
  async getiscreat() {
    const res = await request({ url: "/team/info" });
    console.log(res);
    if (res.data.code == 300) {
      wx.showModal({
        title: "报名出错",
        content: "请先报名",
        showCancel: true,
        cancelText: "返回",
        cancelColor: "#000000",
        confirmText: "前往报名",
        confirmColor: "#3CC51F",
        success: (result) => {
          if (result.confirm) {
            wx.switchTab({
              url: "/pages/signGame/signGame",
            });
          } else {
            wx.switchTab({
              url: "/pages/my/my",
            });
          }
        },
        fail: () => {},
        complete: () => {},
      });
    } else if (res.data.code == 400) {
      this.setData({
        showBut: true,
      });
    } else {
      const team = res.data.data;

      this.setData({
        team,
        showBut: false,
      });
    }
    {
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getiscreat();
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
