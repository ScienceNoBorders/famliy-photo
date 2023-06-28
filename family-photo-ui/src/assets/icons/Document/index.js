import Vue from 'vue'
import SvgIcon from '@/components/SvgIcon'// svg component

// register globally
Vue.component('svg-icon', SvgIcon)

const req = require.context('./svg', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys().map(requireContext)
requireAll(req)


const Buildings = require.context('./Buildings', false, /\.svg$/)
const Business = require.context('./Business', false, /\.svg$/)
const Device = require.context('./Device', false, /\.svg$/)
const Document = require.context('./Document', false, /\.svg$/)
const Others = require.context('./Others', false, /\.svg$/)
const System = require.context('./System', false, /\.svg$/)
const User = require.context('./User', false, /\.svg$/)
requireAll(Buildings)
requireAll(Business)
requireAll(Device)
requireAll(Document)
requireAll(Others)
requireAll(System)
requireAll(User)
