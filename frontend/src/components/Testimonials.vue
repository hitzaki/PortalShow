<template>
  <section class="testimonials-section section-padding">
    <div class="container">
      <h2 class="section-title text-center">客户见证</h2>
      
      <div class="testimonials-list">
        <div v-for="(item, index) in testimonials" :key="index" class="testimonial-row">
          <div class="testimonial-img parallax-img" :style="{ backgroundImage: `url(${item.image})` }"></div>
          <div class="testimonial-text">
            <div class="quote-icon">"</div>
            <p class="quote">{{ item.quote }}</p>
            <div class="author">
              <strong>{{ item.author }}</strong>
              <span>{{ item.position }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted } from 'vue';
import gsap from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';

gsap.registerPlugin(ScrollTrigger);

const testimonials = [
  { 
    quote: "他们的设计完全超出了我们的预期，不仅仅是网站，更是品牌形象的全面升级。", 
    author: "张总", 
    position: "未来科技 CEO",
    image: "https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&w=800&q=80"
  },
  { 
    quote: "极致的交互体验让我们的用户留存率提高了300%，非常专业的团队。", 
    author: "李经理", 
    position: "环球金融 运营总监",
    image: "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=800&q=80"
  }
];

onMounted(() => {
  const rows = document.querySelectorAll('.testimonial-row');
  
  rows.forEach((row, i) => {
    const text = row.querySelector('.testimonial-text');

    // Text Reveal
    gsap.from(text, {
      x: i % 2 === 0 ? 50 : -50,
      opacity: 0,
      duration: 1,
      scrollTrigger: {
        trigger: row,
        start: "top 80%",
      }
    });
  });
});
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.testimonials-list {
  margin-top: 60px;
  display: flex;
  flex-direction: column;
  gap: 100px;
}

.testimonial-row {
  display: flex;
  align-items: center;
  gap: 60px;

  &:nth-child(even) {
    flex-direction: row-reverse;
  }
}

.testimonial-img {
  width: 400px;
  height: 300px;
  border-radius: 20px;
  background-size: cover;
  background-position: center;
  box-shadow: 0 20px 40px rgba(0,0,0,0.4);
  filter: grayscale(0.5);
  transition: filter 0.3s;
  
  &:hover {
    filter: grayscale(0);
  }
}

.testimonial-text {
  flex: 1;
  position: relative;
  
  .quote-icon {
    font-size: 80px;
    color: rgba($color-primary, 0.2);
    font-family: serif;
    position: absolute;
    top: -40px;
    left: -20px;
    z-index: -1;
  }

  .quote {
    font-size: 24px;
    line-height: 1.6;
    margin-bottom: 24px;
    font-weight: 300;
  }

  .author {
    display: flex;
    flex-direction: column;
    
    strong { color: white; font-size: 18px; }
    span { color: $color-text-muted; font-size: 14px; }
  }
}

@media (max-width: $breakpoint-tablet) {
  .testimonial-row {
    flex-direction: column !important;
    gap: 30px;
  }
  
  .testimonial-img {
    width: 100%;
    background-attachment: scroll; // Disable fixed on mobile
  }
}
</style>

