<template>
   <div ref="textBox" :class="['txt', { 'over-hidden': !unfold }]">
      <span
              ref="spanBox"
              :class="['content-remark',{'content-remark-block':displayBox}]"
            >{{ message}}</span>
          </div>
          <div v-if="ifOver" class="btn" @click="unfold = !unfold">{{ unfold ? '收起' : '展开全部' }}</div>
        </div>
</template>

<script>
import { getLessonInfoApi, joinLessonApi } from '@/api/index'

export default {
  name: 'Login',
  components: {},
  data() {
   
    return {
      message: '',
      ifOver: false, // 文本是否超出三行，默认否
      unfold: false, // 文本是否是展开状态 默认为收起
      displayBox: false
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        if (query) {
          this.id = query.id
        }
      },
      immediate: true
    }
  },
  created() {
  },
  mounted() {
    if (this.id) {
      this.initPage()
    } else {
      this.$router.push('404')
    }
  },
  destroyed() {
  },
  methods: {
    initPage(currIdx) {
      try {
        getMessage({ id: this.id }).then(res=>{
          this.message=res;
          this.$nextTick(() => {
          this.ifOver =
            this.$refs.spanBox.offsetHeight > this.$refs.textBox.offsetHeight
          this.displayBox = !this.displayBox
        })
        })
    }
  }
}
</script>

<style lang="scss">
 
.content {
  padding: 1rem;
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  background-repeat: no-repeat;
  align-items: center;
  background: linear-gradient(180deg, #eaf6fd, #e2f1f9, #def0f8);
  background-size: cover;
}
.content-remark {
  text-indent: 2rem;
  font-size: 1rem;
  font-family: PingFangSC-Regular, PingFang SC;
  color: rgba(51, 51, 51, 1);
  line-height: 2rem;
}
.content-remark-block {
  display: block;
  text-indent: 2rem;
  font-size: 1rem;
  font-family: PingFangSC-Regular, PingFang SC;
  color: rgba(51, 51, 51, 1);
  line-height: 2rem;
}

.result {
  display: flex;
  flex-direction: column;
  text-align: center;
  align-items: center;
  padding-top: 100px;
}
.txt {
  font-size: 16px;
  color: #333;
  margin-bottom: 5px;
}
.over-hidden {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
}

.btn {
  color: rgb(66, 66, 66);
  font-weight: bold;
  // font-style: ;
  // position: absolute;
  right: 2rem;
  margin-bottom: 20px;
}
 
</style>
