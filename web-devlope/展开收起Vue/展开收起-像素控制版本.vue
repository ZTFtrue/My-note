<template>
  <div>
    <div class="text-box">
       <p ref="spanBox"
            :class="['content-remark',{'content-remark-height':displayBox}]"
        >{{ message }}</p>
       <div v-show="ifOver"
            class="btn"
            @click="displayBox = !displayBox"
       >{{ !displayBox ? '收起' : '展开全部' }}</div>
    </div>
  </div>
</template>

<script>
import { getLessonInfoApi, joinLessonApi } from "@/api/index";

export default {
  name: "Login",
  components: {},
  data() {
    return {
      message:'',
      ifOver: false, // 文本是否超出三行，默认否
      unfold: false, // 文本是否是展开状态 默认为收起
      displayBox: false,
      originHeight: 0
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query;
        if (query) {
          this.id = query.id;
        }
      },
      immediate: true
    }
  },
  created() {
 
  },
  mounted() {
    this.initPage();
  },
  destroyed() {
  },
  methods: {
    initPage(currIdx) {
      try {
        const res = fetch(('url'),{ id: this.id });
        this.message = res;
        this.$nextTick(() => {
          if (this.originHeight > 90) {
            this.displayBox = true;
            this.ifOver = true;
            this.$refs.spanBox.style.height = 90;
          }
        });
      } catch (err) {
        loading.close();
      }
    }
  }
};
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
.join-box {
  height: 100%;
  width: 100%;
}
.content-remark {
  text-indent: 2rem;
  font-size: 15px;
  font-family: PingFangSC-Regular, PingFang SC;
  color: rgba(51, 51, 51, 1);
  line-height: 30px;
}
.content-remark-height {
  height: 90px;
  overflow: hidden;
  text-overflow: ellipsis;
}
.result {
  display: flex;
  flex-direction: column;
  text-align: center;
  align-items: center;
  padding-top: 100px;
}


.btn {
  color: rgb(66, 66, 66);
  font-weight: bold;
  margin-bottom: 20px;
}
</style>

