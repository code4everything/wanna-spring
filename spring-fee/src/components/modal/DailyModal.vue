<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div class="modal fade" id="daily-modal" tabindex="-1" role="dialog"
       aria-labelledby="daily-modal-label" aria-hidden="true">
    <div class="modal-dialog" :style="{width:isMobile?'90%':'100%'}">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="daily-modal-label">{{title}}</h4>
          <button type="button" class="close" data-dismiss="modal"
                  aria-hidden="true">&times;
          </button>
        </div>
        <div class="modal-body">
          <div class="container data" :data-id="dailies.id">
            <div class="row">
              <div class="col-sm-6 col-6">
                <el-time-picker :placeholder="startTimeTip" class="w-100" value-format="HH:mm:ss"
                                v-model="dailies.startTime"/>
              </div>
              <div class="col-sm-6 col-6">
                <el-time-picker :placeholder="endTimeTip" class="w-100" value-format="HH:mm:ss"
                                v-model="dailies.endTime"/>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12 col-12">
                <el-input :placeholder="contentTip" type="textarea" v-model="dailies.content" rows="3"></el-input>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal"><i
            class="glyphicon glyphicon-remove"></i>{{closeTip}}
          </button>
          <button type="button" class="btn btn-danger" data-dismiss="modal" @click="this.$parent.remove"><i
            class="glyphicon glyphicon-trash"></i>{{removeTip}}
          </button>
          <button type="button" class="btn btn-success" @click="saveDailies"><i
            class="glyphicon glyphicon-floppy-open"></i>{{saveTip}}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import {requestSaveDailies, requestUpdateDailies} from '../../api/api'
import validator from '../../../static/js/validator.min'
import layer from '../../../static/js/layer'

export default {
  name: 'DailyModal',
  data () {
    return {
      startTimeTip: '开始时间',
      endTimeTip: '结束时间',
      title: '编辑日程记录',
      isMobile: false,
      contentTip: '日程内容',
      closeTip: '关闭',
      saveTip: '保存',
      removeTip: '删除'
    }
  },
  props: ['dailies'],
  methods: {
    saveDailies: function () {
      if (this.dailies.startTime || this.dailies.endTime || validator.isEmpty(this.dailies.startTime) || validator.isEmpty(this.dailies.endTime) || validator.isEmpty(this.dailies.content)) {
        layer.alert('数据不能为空')
      } else {
        if (validator.isEmpty(this.dailies.id)) {
          requestSaveDailies(this.$parent.daily.id, this.dailies).then(data => this.handleDailiesReturnData(data))
        } else {
          requestUpdateDailies(this.dailies.id, this.dailies).then(data => this.handleDailiesReturnData(data))
        }
      }
    },
    handleDailiesReturnData: function (data) {
      layer.closeAll()
      if (data.code === 200) {
        this.$parent.updateDailies(data.data)
      } else {
        layer.alert(data.msg)
      }
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
  }
}
</script>

<style scoped>

</style>
