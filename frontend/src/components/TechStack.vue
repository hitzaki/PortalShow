<template>
  <section id="tech" class="tech-section section-padding">
    <div class="container">
      <h2 class="section-title text-center">核心技术栈</h2>
      
      <div class="carousel-container">
        <div class="carousel-scene">
          <div class="carousel-rotate" ref="carouselRef">
            <div 
              v-for="(tech, index) in techStack" 
              :key="index" 
              class="tech-card"
              :style="getCardStyle(index)"
            >
              <div class="tech-icon">{{ tech.icon }}</div>
              <h3>{{ tech.name }}</h3>
              <p>{{ tech.desc }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

const techStack = [
  { name: "Vue 3", icon: "⚡", desc: "响应式核心" },
  { name: "Vite", icon: "🚀", desc: "极速构建" },
  { name: "GSAP", icon: "🎬", desc: "复杂动画" },
  { name: "Three.js", icon: "🧊", desc: "3D 体验" },
  { name: "SCSS", icon: "🎨", desc: "高级样式" },
  { name: "TypeScript", icon: "📘", desc: "类型安全" }
];

const carouselRef = ref(null);
let rotation = 0;
let autoRotate;

const getCardStyle = (index) => {
  const count = techStack.length;
  const angle = 360 / count;
  const theta = angle * index;
  const radius = 250; // Distance from center
  
  return {
    transform: `rotateY(${theta}deg) translateZ(${radius}px)`
  };
};

onMounted(() => {
  autoRotate = setInterval(() => {
    rotation -= 0.5;
    if (carouselRef.value) {
      carouselRef.value.style.transform = `rotateY(${rotation}deg)`;
    }
  }, 16); // ~60fps
});

onUnmounted(() => {
  clearInterval(autoRotate);
});
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.tech-section {
  overflow: hidden;
  background: radial-gradient(circle at center, #1e293b 0%, $color-bg-dark 80%);
}

.carousel-container {
  height: 400px;
  display: flex;
  justify-content: center;
  align-items: center;
  perspective: 1000px;
  margin-top: 40px;
}

.carousel-scene {
  width: 260px;
  height: 300px;
  position: relative;
  transform-style: preserve-3d;
}

.carousel-rotate {
  width: 100%;
  height: 100%;
  position: absolute;
  transform-style: preserve-3d;
  // Animation handled by JS for continuous smooth rotation
}

.tech-card {
  position: absolute;
  left: 0;
  top: 0;
  width: 260px;
  height: 300px;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  text-align: center;
  box-shadow: 0 0 20px rgba($color-primary, 0.2);
  backface-visibility: visible; // Visible from back? Maybe hidden looks better. Let's keep visible for glass effect.

  .tech-icon {
    font-size: 48px;
    margin-bottom: 20px;
  }

  h3 {
    color: $color-primary;
    margin-bottom: 10px;
  }

  p {
    color: $color-text-muted;
    font-size: 14px;
  }
}
</style>

