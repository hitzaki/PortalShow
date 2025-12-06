<template>
  <section id="services" class="timeline-section section-padding">
    <div class="container">
      <h2 class="section-title text-center">全流程数字化服务</h2>
      <p class="section-desc text-center">从概念到落地，我们提供端到端的专业解决方案</p>
      
      <div class="timeline-container" ref="timelineContainer">
        <div class="timeline-line"></div>
        
        <div v-for="(item, index) in timelineItems" :key="index" class="timeline-item" :class="index % 2 === 0 ? 'left' : 'right'">
          <div class="timeline-dot">
            <div class="dot-inner"></div>
          </div>
          
          <div class="timeline-content card-hover">
            <div class="content-icon">
              <!-- Using simple CSS shapes/text instead of SVG for brevity, or simplified icons -->
              <span>{{ item.icon }}</span>
            </div>
            <h3>{{ item.title }}</h3>
            <p>{{ item.desc }}</p>
            <div class="content-bg" v-if="item.bgType === 'code'">
              <!-- Code rain effect placeholder -->
              <div class="code-lines"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import gsap from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';

gsap.registerPlugin(ScrollTrigger);

const timelineItems = [
  { title: "需求沟通", desc: "深度解析业务场景，定制数字化战略蓝图。", icon: "💭", bgType: "none" },
  { title: "设计呈现", desc: "国际级UI/UX设计，打造极致视觉体验。", icon: "🎨", bgType: "design" },
  { title: "开发实现", desc: "Vue 3 + Vite 高性能架构，代码整洁强健。", icon: "💻", bgType: "code" },
  { title: "上线交付", desc: "全方位测试与SEO优化，助您业务起飞。", icon: "🚀", bgType: "rocket" }
];

const timelineContainer = ref(null);

onMounted(() => {
  const items = timelineContainer.value.querySelectorAll('.timeline-item');
  
  // Line Animation
  gsap.fromTo('.timeline-line', 
    { scaleY: 0 },
    {
      scaleY: 1,
      transformOrigin: "top center",
      ease: "none",
      scrollTrigger: {
        trigger: timelineContainer.value,
        start: "top center",
        end: "bottom center",
        scrub: 1
      }
    }
  );

  // Items Animation
  items.forEach((item, i) => {
    const content = item.querySelector('.timeline-content');
    const dot = item.querySelector('.timeline-dot');
    const direction = i % 2 === 0 ? -100 : 100;

    gsap.from(content, {
      x: direction,
      opacity: 0,
      duration: 1,
      ease: "power3.out",
      scrollTrigger: {
        trigger: item,
        start: "top 80%",
        end: "bottom 20%",
        toggleActions: "play none none reverse"
      }
    });

    gsap.from(dot, {
      scale: 0,
      opacity: 0,
      duration: 0.5,
      delay: 0.2,
      scrollTrigger: {
        trigger: item,
        start: "top 80%",
      }
    });
  });
});
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.section-title {
  font-size: 40px;
  margin-bottom: 16px;
}

.section-desc {
  color: $color-text-muted;
  margin-bottom: 80px;
  font-size: 18px;
}

.text-center { text-align: center; }

.timeline-container {
  position: relative;
  max-width: 1000px;
  margin: 0 auto;
  padding: 40px 0;
}

.timeline-line {
  position: absolute;
  left: 50%;
  top: 0;
  bottom: 0;
  width: 2px;
  background: rgba($color-secondary, 0.3);
  transform: translateX(-50%);
}

.timeline-item {
  display: flex;
  justify-content: center;
  padding-bottom: 80px;
  position: relative;
  width: 100%;

  .timeline-dot {
    position: absolute;
    left: 50%;
    top: 0;
    width: 20px;
    height: 20px;
    transform: translateX(-50%);
    background: $color-bg-dark;
    border: 2px solid $color-secondary;
    border-radius: 50%;
    z-index: 2;
    display: flex;
    align-items: center;
    justify-content: center;

    .dot-inner {
      width: 8px;
      height: 8px;
      background: $color-secondary;
      border-radius: 50%;
      box-shadow: 0 0 10px $color-secondary;
    }
  }

  .timeline-content {
    width: 45%;
    background: $color-bg-card;
    padding: 32px;
    border-radius: 16px;
    border: 1px solid rgba(255,255,255,0.05);
    position: relative;
    overflow: hidden;
    transition: transform 0.3s;
    
    &:hover {
      transform: translateY(-5px);
      border-color: rgba($color-primary, 0.3);
      box-shadow: 0 10px 30px -5px rgba(0,0,0,0.3);
    }

    .content-icon {
      font-size: 32px;
      margin-bottom: 16px;
    }

    h3 {
      font-size: 24px;
      margin-bottom: 12px;
      color: white;
    }

    p {
      color: $color-text-muted;
    }
  }

  &.left {
    justify-content: flex-start;
    .timeline-content { margin-right: auto; }
  }

  &.right {
    justify-content: flex-end;
    .timeline-content { margin-left: auto; }
  }
}

// Code rain bg effect
.code-lines {
  position: absolute;
  top: 0; right: 0;
  width: 100%; height: 100%;
  background: linear-gradient(180deg, transparent, rgba($color-primary, 0.05));
  z-index: -1;
  opacity: 0.5;
}

@media (max-width: $breakpoint-tablet) {
  .timeline-line { left: 20px; }
  .timeline-item {
    justify-content: flex-start !important;
    padding-left: 50px;
    
    .timeline-dot { left: 20px; }
    .timeline-content { width: 100%; }
  }
}
</style>

