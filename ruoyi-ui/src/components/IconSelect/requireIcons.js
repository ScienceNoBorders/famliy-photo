
// const req = require.context('../../assets/icons/svg', false, /\.svg$/)
// const requireAll = requireContext => requireContext.keys()

// const re = /\.\/(.*)\.svg/

// const icons = requireAll(req).map(i => {
//   return i.match(re)[1]
// })

// export default icons




const req = require.context('../../assets/icons/svg', false, /\.svg$/)
const Buildings = require.context('../../assets/icons/Buildings', false, /\.svg$/)
const Business = require.context('../../assets/icons/Business', false, /\.svg$/)
const Device = require.context('../../assets/icons/Device', false, /\.svg$/)
const Document = require.context('../../assets/icons/Document', false, /\.svg$/)
const Others = require.context('../../assets/icons/Others', false, /\.svg$/)
const System = require.context('../../assets/icons/System', false, /\.svg$/)
const User = require.context('../../assets/icons/User', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys()

const re = /\.\/(.*)\.svg/

const svgIcons = requireAll(req).map(i => {
  return i.match(re)[1]
})
const BuildingsIcons = requireAll(Buildings).map(i => {
  return i.match(re)[1]
})
const BusinessIcons = requireAll(Business).map(i => {
  return i.match(re)[1]
})
const DeviceIcons = requireAll(Device).map(i => {
  return i.match(re)[1]
})
const DocumentIcons = requireAll(Document).map(i => {
  return i.match(re)[1]
})
const OthersIcons = requireAll(Others).map(i => {
  return i.match(re)[1]
})
const SystemIcons = requireAll(System).map(i => {
  return i.match(re)[1]
})
const UserIcons = requireAll(User).map(i => {
  return i.match(re)[1]
})
const icons = [
  {
    classifyName:'用户',
    iconList:UserIcons
  },
  {
    classifyName:'建筑',
    iconList:BuildingsIcons
  },
  {
    classifyName:'办公',
    iconList:BusinessIcons
  },
  {
    classifyName:'设备',
    iconList:DeviceIcons
  },
  {
    classifyName:'文档',
    iconList:DocumentIcons
  },
  {
    classifyName:'系统',
    iconList:SystemIcons
  },
  {
    classifyName:'其他',
    iconList:OthersIcons
  },
  {
    classifyName:'默认',
    iconList:svgIcons
  }
]

export default icons
