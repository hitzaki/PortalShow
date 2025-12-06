<template>
  <section class="stats-section section-padding">
    <div class="container">
      <div class="stats-grid">
        <!-- Counters -->
        <div class="counters-wrapper">
          <div class="stat-item" v-for="(stat, index) in stats" :key="index">
            <div class="stat-number">
              <span :ref="el => counterRefs[index] = el">0</span>{{ stat.suffix }}
            </div>
            <div class="stat-label">{{ stat.label }}</div>
          </div>
        </div>

        <!-- Radar Chart -->
        <div class="chart-wrapper">
          <div ref="chartRef" class="radar-chart"></div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import gsap from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import * as echarts from 'echarts';

gsap.registerPlugin(ScrollTrigger);

const stats = [
  { value: 150, label: "服务客户", suffix: "+" },
  { value: 98, label: "好评率", suffix: "%" },
  { value: 32, label: "行业奖项", suffix: "项" },
  { value: 5, label: "年深耕经验", suffix: "+" }
];

const counterRefs = ref([]);
const chartRef = ref(null);
let myChart = null;

onMounted(() => {
  // Counter Animation
  counterRefs.value.forEach((el, index) => {
    gsap.to(el, {
      innerText: stats[index].value,
      duration: 2,
      snap: { innerText: 1 },
      scrollTrigger: {
        trigger: el,
        start: "top 85%",
      }
    });
  });

  // ECharts Radar
  if (chartRef.value) {
    myChart = echarts.init(chartRef.value);
    const option = {
      radar: {
        radius: '70%',
        center: ['50%', '50%'],
        axisName: {
          color: 'rgba(255, 255, 255, 0.8)',
          fontSize: 14
        },
        indicator: [
          { name: 'UI设计', max: 100 },
          { name: '前端开发', max: 100 },
          { name: '交互动画', max: 100 },
          { name: '性能优化', max: 100 },
          { name: 'SEO', max: 100 },
          { name: '后端架构', max: 100 }
        ],
        splitArea: {
          areaStyle: {
            color: ['rgba(37, 99, 235, 0.1)', 'rgba(37, 99, 235, 0.05)']
          }
        },
        axisLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.3)' } },
        splitLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.1)' } }
      },
      series: [{
        type: 'radar',
        data: [{
          value: [95, 98, 90, 85, 88, 92],
          name: '能力评估',
          areaStyle: { color: 'rgba(139, 92, 246, 0.5)' },
          lineStyle: { color: '#8b5cf6' }
        }]
      }]
    };
    myChart.setOption(option);
    
    window.addEventListener('resize', () => myChart.resize());
  }
});

onUnmounted(() => {
  if (myChart) myChart.dispose();
});
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.stats-section {
  background: linear-gradient(to bottom, $color-bg-dark, #1e293b);
}

.stats-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 60px;
  align-items: center;
}

.counters-wrapper {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
}

.stat-item {
  text-align: center;
  padding: 30px;
  background: rgba(255,255,255,0.03);
  border-radius: 16px;
  border: 1px solid rgba(255,255,255,0.05);
  
  .stat-number {
    font-size: 48px;
    font-weight: 800;
    color: $color-primary;
    font-family: $font-display;
    margin-bottom: 10px;
  }

  .stat-label {
    color: $color-text-muted;
  }
}

.chart-wrapper {
  height: 500px;
  display: flex;
  justify-content: center;
  align-items: center;
  
  .radar-chart {
    width: 100%;
    height: 100%;
  }
}

@media (max-width: $breakpoint-tablet) {
  .stats-grid {
    grid-template-columns: 1fr;
  }
}
</style>

