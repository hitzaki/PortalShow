<template>
  <nav class="navbar" :class="{ 'scrolled': isScrolled }">
    <div class="container nav-inner">
      <div class="logo">
        <span class="logo-text">Portal<span class="highlight">Show</span></span>
      </div>
      
      <div class="nav-links desktop-only">
        <a href="#home" class="nav-item" @click.prevent="scrollTo('#home')">首页</a>
        <a href="#services" class="nav-item" @click.prevent="scrollTo('#services')">服务</a>
        <a href="#cases" class="nav-item" @click.prevent="scrollTo('#cases')">案例</a>
        <a href="#tech" class="nav-item" @click.prevent="scrollTo('#tech')">技术</a>
        <a href="#contact" class="nav-item btn-primary" @click.prevent="scrollTo('#contact')">开始合作</a>
      </div>

      <div class="mobile-menu-btn" @click="toggleMenu">
        <div class="bar" :class="{ 'open': isMenuOpen }"></div>
        <div class="bar" :class="{ 'open': isMenuOpen }"></div>
        <div class="bar" :class="{ 'open': isMenuOpen }"></div>
      </div>
    </div>

    <!-- Mobile Menu Overlay -->
    <transition name="fade">
      <div v-if="isMenuOpen" class="mobile-menu">
         <a href="#home" @click="closeMenu('#home')">首页</a>
         <a href="#services" @click="closeMenu('#services')">服务</a>
         <a href="#cases" @click="closeMenu('#cases')">案例</a>
         <a href="#contact" @click="closeMenu('#contact')">联系我们</a>
      </div>
    </transition>
  </nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import gsap from 'gsap';
import ScrollToPlugin from 'gsap/ScrollToPlugin';

gsap.registerPlugin(ScrollToPlugin);

const isScrolled = ref(false);
const isMenuOpen = ref(false);

const checkScroll = () => {
  isScrolled.value = window.scrollY > 50;
};

const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value;
};

const scrollTo = (selector) => {
  gsap.to(window, { duration: 1, scrollTo: { y: selector, offsetY: 80 }, ease: "power3.inOut" });
};

const closeMenu = (selector) => {
  isMenuOpen.value = false;
  scrollTo(selector);
};

onMounted(() => {
  window.addEventListener('scroll', checkScroll);
});

onUnmounted(() => {
  window.removeEventListener('scroll', checkScroll);
});
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
  padding: 20px 0;
  transition: all $transition-fast;
  
  &.scrolled {
    padding: 15px 0;
    @include glass-morphism(0.8, 20px);
    background: rgba($color-bg-dark, 0.8);
    border-bottom: 1px solid rgba(255,255,255,0.05);
  }

  .nav-inner {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .logo {
    font-size: 24px;
    font-weight: 800;
    font-family: $font-display;
    letter-spacing: -0.5px;
    
    .highlight {
      @include text-gradient;
    }
  }

  .nav-links {
    display: flex;
    gap: 32px;
    align-items: center;

    .nav-item {
      font-size: 14px;
      font-weight: 500;
      opacity: 0.8;
      position: relative;

      &:hover {
        opacity: 1;
        color: $color-primary;
      }

      &::after {
        content: '';
        position: absolute;
        bottom: -4px;
        left: 0;
        width: 0%;
        height: 2px;
        background: $color-primary;
        transition: width $transition-fast;
      }

      &:hover::after {
        width: 100%;
      }
    }

    .btn-primary {
      padding: 10px 24px;
      background: linear-gradient(135deg, $color-primary, $color-secondary);
      border-radius: 30px;
      color: white;
      opacity: 1;
      font-weight: 600;
      
      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 10px 20px -10px rgba($color-primary, 0.5);
      }
      
      &::after { display: none; }
    }
  }
}

.mobile-menu-btn {
  display: none;
  flex-direction: column;
  gap: 6px;
  cursor: pointer;

  .bar {
    width: 24px;
    height: 2px;
    background: white;
    transition: 0.3s;

    &.open:nth-child(1) { transform: rotate(45deg) translate(5px, 6px); }
    &.open:nth-child(2) { opacity: 0; }
    &.open:nth-child(3) { transform: rotate(-45deg) translate(5px, -6px); }
  }
}

.mobile-menu {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background: $color-bg-dark;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 30px;
  z-index: 999;

  a {
    font-size: 24px;
    font-weight: 700;
  }
}

@media (max-width: $breakpoint-tablet) {
  .desktop-only { display: none !important; }
  .mobile-menu-btn { display: flex; }
}
</style>

