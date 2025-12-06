<template>
  <section id="cases" class="gallery-section section-padding">
    <div class="container">
      <h2 class="section-title text-center">行业案例精选</h2>
      
      <div class="gallery-grid">
        <div 
          v-for="(project, index) in projects" 
          :key="index" 
          class="project-card"
          @mousemove="handleMouseMove($event, index)"
          @mouseleave="handleMouseLeave(index)"
          ref="cardRefs"
        >
          <div class="card-content" :style="cardStyles[index]">
            <div class="card-image" :style="{ backgroundImage: `url(${project.image})` }"></div>
            <div class="card-overlay"></div>
            <div class="card-info">
              <h4>{{ project.category }}</h4>
              <h3>{{ project.title }}</h3>
              <button class="btn-text">查看详情 +</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';

const projects = [
  { title: "未来金融科技门户", category: "FinTech", image: "https://images.unsplash.com/photo-1639322537228-f710d846310a?auto=format&fit=crop&w=800&q=80" },
  { title: "全球物流数据中心", category: "Logistics", image: "https://images.unsplash.com/photo-1586528116311-ad8dd3c8310d?auto=format&fit=crop&w=800&q=80" },
  { title: "高端医疗健康平台", category: "Healthcare", image: "https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?auto=format&fit=crop&w=800&q=80" },
  { title: "智能家居生态系统", category: "Smart Home", image: "https://images.unsplash.com/photo-1558002038-1091a166111c?auto=format&fit=crop&w=800&q=80" },
  { title: "创意设计工作室", category: "Creative", image: "https://images.unsplash.com/photo-1600607686527-6fb886090705?auto=format&fit=crop&w=800&q=80" },
  { title: "新能源汽车官网", category: "Automotive", image: "https://images.unsplash.com/photo-1617788138017-80ad40651399?auto=format&fit=crop&w=800&q=80" }
];

const cardRefs = ref([]);
const cardStyles = ref(projects.map(() => ({})));

const handleMouseMove = (e, index) => {
  const card = e.currentTarget;
  const rect = card.getBoundingClientRect();
  const x = e.clientX - rect.left;
  const y = e.clientY - rect.top;
  
  const centerX = rect.width / 2;
  const centerY = rect.height / 2;
  
  const rotateX = ((y - centerY) / centerY) * -10; // Max 10deg
  const rotateY = ((x - centerX) / centerX) * 10;

  cardStyles.value[index] = {
    transform: `perspective(1000px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) scale(1.02)`,
    transition: 'transform 0.1s ease-out' // Quick response
  };
};

const handleMouseLeave = (index) => {
  cardStyles.value[index] = {
    transform: `perspective(1000px) rotateX(0deg) rotateY(0deg) scale(1)`,
    transition: 'transform 0.5s ease-out' // Smooth return
  };
};
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.gallery-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 40px;
  padding: 40px 0;
}

.project-card {
  height: 400px;
  cursor: pointer;
  
  // Container for 3D effect
  perspective: 1000px;
}

.card-content {
  width: 100%;
  height: 100%;
  position: relative;
  border-radius: 20px;
  overflow: hidden;
  background: $color-bg-card;
  box-shadow: 0 20px 40px -10px rgba(0,0,0,0.3);
  border: 1px solid rgba(255,255,255,0.05);
  transform-style: preserve-3d; // Crucial for parallax children
}

.card-image {
  position: absolute;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background-size: cover;
  background-position: center;
  transition: transform 0.5s;
  
  // Slight zoom on hover handled by parent transform usually, 
  // but we can add extra depth here if we want parallax
}

.card-overlay {
  position: absolute;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background: linear-gradient(to bottom, transparent 40%, rgba(15, 23, 42, 0.9) 100%);
  opacity: 0.8;
  transition: opacity 0.3s;
}

.card-info {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  padding: 30px;
  transform: translateZ(20px); // Pop out text
  
  h4 {
    color: $color-accent;
    font-size: 14px;
    text-transform: uppercase;
    letter-spacing: 1px;
    margin-bottom: 8px;
  }

  h3 {
    font-size: 24px;
    color: white;
    margin-bottom: 16px;
  }

  .btn-text {
    background: none;
    color: white;
    font-size: 14px;
    opacity: 0;
    transform: translateY(10px);
    transition: all 0.3s;
  }
}

.project-card:hover {
  .card-image {
    transform: scale(1.1); // Image zooms inside card
  }
  
  .card-overlay {
    opacity: 0.6; // Brighter on hover
  }

  .btn-text {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>

