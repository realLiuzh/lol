let ajaxTimes = 0;
var app = getApp()
export const request=(params)=>{
  //判断是否又token 没有就带上
  const openid = wx.getStorageSync('openid');
   let header = {...params.header};
   let data = {...params.data,openid};
    // if(!params.url.includes("/file/")){
    //   //拼接上header 带上token
    //   let data = {...params.data,code:app.globalData.code};
    // }

  ajaxTimes++;
  //显示加载中效果
   wx.showLoading({
     title: "加载中",
     mask: true,
   });
  //定义公共的url
  const  baseUrl="https://www.csomlh.com/lol"
  return new Promise((resolve,reject)=>{
    wx.request({
      ...params,//对传进来的值进行解构
      header:header,
      data:data,
      url:baseUrl+params.url,
      success:(result)=>{
        resolve(result);
      },
      fail:(err)=>{
        reject(err);
      },
      complete:()=>{
        ajaxTimes--;
        //关闭图标
        if(ajaxTimes === 0){
          wx.hideLoading();
        }
      }
    })
  })
}