<template>
  <div class="app-container" :class="{ 'loaded': isLoaded }">
    <!-- Loading Screen -->
    <div class="loader" v-if="!isIntroDone">
      <div class="loader-logo">Portal<span class="highlight">Show</span></div>
      <div class="progress-bar">
        <div class="progress" :style="{ width: loadProgress + '%' }"></div>
      </div>
    </div>

    <!-- Main Content -->
    <main v-show="isIntroDone">
      <CustomCursor />
      <NavBar ref="navRef" />
      
      <HeroSection />
      <ServiceTimeline />
      <ProjectGallery />
      <TechStack />
      <DataStats />
      <Testimonials />
      <ContactCTA />
      
      <footer class="site-footer">
        <div class="container">
          <div class="footer-content">
            <div class="footer-logo">PortalShow</div>
            <p>&copy; 2024 PortalShow Digital Agency. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import CustomCursor from './components/CustomCursor.vue';
import NavBar from './components/NavBar.vue';
import HeroSection from './components/HeroSection.vue';
import ServiceTimeline from './components/ServiceTimeline.vue';
import ProjectGallery from './components/ProjectGallery.vue';
import TechStack from './components/TechStack.vue';
import DataStats from './components/DataStats.vue';
import Testimonials from './components/Testimonials.vue';
import ContactCTA from './components/ContactCTA.vue';
import gsap from 'gsap';

const isLoaded = ref(false);
const isIntroDone = ref(false);
const loadProgress = ref(0);
const navRef = ref(null);

onMounted(() => {
  // Simulate Loading
  const interval = setInterval(() => {
    if (loadProgress.value < 100) {
      loadProgress.value += Math.random() * 10;
    } else {
      clearInterval(interval);
      loadProgress.value = 100;
      finishLoading();
    }
  }, 100);
});

const finishLoading = () => {
  setTimeout(() => {
    isLoaded.value = true;
    
    // Exit Animation for Loader
    gsap.to('.loader', {
      opacity: 0,
      duration: 0.8,
      onComplete: () => {
        isIntroDone.value = true;
        // Trigger Intro Animations for Main Content
        animateIntro();
      }
    });
  }, 500);
};

const animateIntro = () => {
  // Next tick to ensure DOM is rendered
  setTimeout(() => {
    gsap.from('.navbar', { y: -50, opacity: 0, duration: 1, ease: 'power3.out' });
  }, 100);
};
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.loader {
  position: fixed;
  top: 0; left: 0;
  width: 100%; height: 100vh;
  background: $color-bg-dark;
  z-index: 9999;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  
  .loader-logo {
    font-size: 40px;
    font-family: $font-display;
    font-weight: 800;
    margin-bottom: 20px;
    
    .highlight { color: $color-primary; }
  }
  
  .progress-bar {
    width: 200px;
    height: 4px;
    background: rgba(255,255,255,0.1);
    border-radius: 2px;
    overflow: hidden;
    
    .progress {
      height: 100%;
      background: $color-primary;
      transition: width 0.2s;
    }
  }
}

.site-footer {
  padding: 40px 0;
  border-top: 1px solid rgba(255,255,255,0.05);
  text-align: center;
  color: $color-text-muted;
  
  .footer-logo {
    font-size: 20px;
    font-weight: 700;
    color: white;
    margin-bottom: 10px;
  }
}
</style>

