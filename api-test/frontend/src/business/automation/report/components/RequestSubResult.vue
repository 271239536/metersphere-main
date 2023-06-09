<template>
  <div class="request-result">
    <p class="el-divider--horizontal"></p>
    <div @click="active">
      <el-row :gutter="10" type="flex" align="middle" class="info">
        <el-col :span="6" v-if="indexNumber != undefined">
          <div class="method">
            <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">
              <div class="el-step__icon is-text ms-api-col" v-if="indexNumber % 2 == 0">
                <div class="el-step__icon-inner">{{ indexNumber + 1 }}</div>
              </div>
              <div class="el-step__icon is-text ms-api-col-create" v-else>
                <div class="el-step__icon-inner">{{ indexNumber + 1 }}</div>
              </div>
              <i class="icon el-icon-arrow-right" :class="{ 'is-active': isActive }" @click="active" @click.stop />
              {{ getName(request.name) }}
            </div>
          </div>
        </el-col>
        <el-col :span="2">
          <div>
            {{ request.method }}
          </div>
        </el-col>
        <el-col :span="6">
          <div class="url">
            <el-tooltip
              :content="request.url"
              style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap"
              placement="bottom"
              :open-delay="800">
              <div>
                {{ request.url }}
              </div>
            </el-tooltip>
          </div>
        </el-col>
        <el-col :span="5">
          <el-tooltip effect="dark" :content="request.responseResult.responseCode" placement="bottom" :open-delay="800">
            <div class="url" style="color: #5daf34">
              {{ request.responseResult.responseCode }}
            </div>
          </el-tooltip>
        </el-col>
        <el-col :span="3"> {{ request.responseResult.responseTime }} ms </el-col>

        <el-col :span="2">
          <div class="success">
            <el-tag size="mini" type="success" v-if="request.success"> Success </el-tag>
            <el-tag size="mini" type="danger" v-else> Error </el-tag>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-collapse-transition>
      <div v-show="isActive" style="width: 99%">
        <ms-request-sub-result-tail
          :scenario-name="scenarioName"
          :request-type="requestType"
          v-if="isActive"
          :request="request" />
      </div>
    </el-collapse-transition>
  </div>
</template>

<script>
import MsRequestMetric from './RequestMetric';
import MsAssertionResults from './AssertionResults';
import MsRequestText from './RequestText';
import MsResponseText from './ResponseText';
import MsRequestSubResultTail from './RequestSubResultTail';

export default {
  name: 'MsRequestSubResult',
  components: {
    MsResponseText,
    MsRequestText,
    MsAssertionResults,
    MsRequestMetric,
    MsRequestSubResultTail,
  },
  props: {
    request: Object,
    scenarioName: String,
    indexNumber: Number,
  },
  data() {
    return { isActive: false, requestType: undefined };
  },
  methods: {
    active() {
      this.isActive = !this.isActive;
    },
    getName(name) {
      if (name && name.indexOf('<->') !== -1) {
        return name.split('<->')[0];
      }
      if (name && name.indexOf('^@~@^') !== -1) {
        let arr = name.split('^@~@^');
        let value = arr[arr.length - 1];
        if (value.indexOf('UUID=') !== -1) {
          return value.split('UUID=')[0];
        }
        if (value && value.startsWith('UUID=')) {
          return '';
        }
        if (value && value.indexOf('<->') !== -1) {
          return value.split('<->')[0];
        }
        return value;
      }
      if (name && name.startsWith('UUID=')) {
        return '';
      }
      return name;
    },
  },
};
</script>

<style scoped>
.request-result {
  width: 100%;
  min-height: 40px;
  padding: 2px 0;
}

.request-result .info {
  margin-left: 20px;
  cursor: pointer;
}

.request-result .method {
  color: #1e90ff;
  font-size: 14px;
  font-weight: 500;
  line-height: 40px;
  padding-left: 5px;
}

.request-result .url {
  color: #7f7f7f;
  font-size: 12px;
  font-weight: 400;
  margin-top: 4px;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  word-break: break-all;
}

.request-result .tab .el-tabs__header {
  margin: 0;
}

.request-result .text {
  height: 300px;
  overflow-y: auto;
}

.sub-result .info {
  background-color: #fff;
}

.sub-result .method {
  border-left: 5px solid #1e90ff;
  padding-left: 20px;
}

.sub-result:last-child {
  border-bottom: 1px solid #ebeef5;
}

.ms-api-col {
  background-color: #eff0f0;
  border-color: #eff0f0;
  margin-right: 10px;
  font-size: 12px;
  color: #64666a;
}

.ms-api-col-create {
  background-color: #ebf2f2;
  border-color: #008080;
  margin-right: 10px;
  font-size: 12px;
  color: #008080;
}

.el-divider--horizontal {
  margin: 2px 0;
  background: 0 0;
  border-top: 1px solid #e8eaec;
}

.icon.is-active {
  transform: rotate(90deg);
}
</style>
