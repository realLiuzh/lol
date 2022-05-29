// components/list/list.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    listTime:{
      type:Array,
      value:[]
    },
    listPoint:{
      type:Array,
      value:[]
    }
  
  },

  /**
   * 组件的初始数据
   */
  data: {
    afterPoint:[],//后七名
    beforePoint:[],//前三名
  },

  lifetimes: {
    attached: function() {
      console.log(this)
      const afterPoint =this.data.listPoint.slice(3);
      const beforePoint =this.data.listPoint.slice(0,3);
      this.setData({
        afterPoint,
        beforePoint
      })
    },
  },
  /**
   * 组件的方法列表
   */

  methods: {

  }
})
