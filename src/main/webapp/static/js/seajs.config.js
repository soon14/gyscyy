Aptech.LOCALE = 'zh-cn';
Aptech.THEME = 'default';

seajs.config({

  // 别名配置
  alias: {
    'datatables': 'asset/datatables',
    'datetimepicker': 'asset/datetimepicker',
    'tree': 'asset/tree',
    'combotree': 'asset/combotree',
    'combobox': 'asset/combobox',
    'my97datepicker': 'asset/my97datepicker',
    'dialog': 'asset/dialog',
    'confirm': 'asset/bootbox',
    'echarts': 'asset/echarts',
    'uploadify': 'asset/uploadify',
    'uploadifyBatch': 'asset/uploadifyBatch',
    'userselectbox': 'asset/userselectbox',
    'selectbox': 'asset/selectbox',
    'dutiesselectbox': 'asset/dutiesselectbox',
    'upnextstrip':'asset/upnextstrip',
    'uploaddropzone':'asset/uploaddropzone',
    'duallistbox': 'asset/duallistbox',
    'wysiwyg':'asset/wysiwyg',
    'mask':'asset/mask',
  },

  // 路径配置
  paths: {
    'asset': '{ctx}/static/js/asset'
  },

  // 变量配置
  vars: {
    'locale': Aptech.LOCALE || 'zh-cn', 	//本地化操作
    'theme': Aptech.THEME || 'default', 	//主题设置
    'ctx': Aptech.CONTEXT_ROOT_PATH || 'common'
  },

  // 映射配置
//  map: [
//    ['http://example.com/js/app/', 'http://localhost/js/app/']
//  ],

  // 预加载项
//  preload: [
//    Function.prototype.bind ? '' : 'es5-safe',
//    this.JSON ? '' : 'json'
//  ],

  // 调试模式
  debug: true,

  // Sea.js 的基础路径
  base: '/',

  // 文件编码
  charset: 'utf-8'
});