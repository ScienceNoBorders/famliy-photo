<template>
  <div ref="portaletDiv">
    <div ref="commandstats" style="height: 300px" />
  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  name: 'Cache',
  filters: {},
  data() {
    return {
      commandstats: null
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {
    this.getFirstChart()
    window.addEventListener('resize', () => {
      this.commandstats.resize()
    })
    this.$emit('setHeight', this.$refs.portaletDiv.offsetHeight)
  },
  methods: {
    getFirstChart() {
      this.commandstats = echarts.init(this.$refs.commandstats, 'macarons')
      this.commandstats.setOption({
        title: {
          text: '',
          textStyle: {
            fontSize: 16,
            fontWeight: '600',
            color: '#333' // 主标题文字颜色
          },
          left: 8,
          top: 8
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          top: 0,
          right: 20,
          textStyle: {
            color: '#666'
          },
          itemGap: 20,
          itemWidth: 10,
          data: ['2020年', '2021年']
        },
        toolbox: {
          feature: {}
        },
        grid: {
          top: '8%',
          left: '1%',
          right: '1%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: true,
            data: [
              '2021-1',
              '2021-2',
              '2021-3',
              '2021-4',
              '2021-5',
              '2021-6',
              '2021-7',
              '2021-8',
              '2021-9',
              '2021-10',
              '2021-11',
              '2021-12'
            ],
            axisLabel: {
              // 坐标轴文本标签，详见axis.axisLabel
              show: true,
              rotate: 0,
              margin: 8,
              textStyle: {
                color: '#666',
                fontSize: '12'
              }
            },
            axisLine: {
              lineStyle: {
                color: '#dfe6ff',
                width: 1
              }
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            textStyle: {
              color: '#666',
              fontSize: '12'
            },
            axisLine: {
              show: false,
              lineStyle: {
                color: '#666',
                width: 1
              }
            }
          }
        ],
        series: [
          {
            smooth: false,
            name: '开发人员',
            type: 'line',
            stack: '总量',
            itemStyle: {
              color: '#5e91f9'
            },
            symbolSize: 10,
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgba(94,145,249,0.2)'
                },
                {
                  offset: 1,
                  color: 'rgba(94,145,249,0.01)'
                }
              ])
            },
            data: [
              21,
              23,
              11,
              37,
              24,
              67,
              78,
              38,
              88,
              56,
              12,
              34,
              14
            ]
          },
          {
            smooth: false,
            name: '设计人员',
            type: 'line',
            stack: '总量',
            symbolSize: 10,
            itemStyle: {
              color: '#5cd8a7'
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgba(92,216,167,0.2)'
                },
                {
                  offset: 1,
                  color: 'rgba(92,216,167,0.01)'
                }
              ])
            },
            data: [
              47,
              36,
              11,
              46,
              32,
              67,
              24,
              39,
              31,
              9,
              32,
              21,
              60

            ]
          }
        ]
      })
    }
  }
}
</script>

<style></style>
