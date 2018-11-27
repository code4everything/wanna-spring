<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div aria-hidden="true" aria-labelledby="daily-modal-label" class="modal fade" id="daily-modal"
       role="dialog" tabindex="-1">
    <div :style="{width:isMobile?'90%':'100%'}" class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="daily-modal-label">{{title}}</h4>
          <button aria-hidden="true" class="close" data-dismiss="modal"
                  type="button">&times;
          </button>
        </div>
        <div class="modal-body">
          <div :data-id="dailies.id" class="container data">
            <div class="row">
              <div class="col-sm-6 col-6">
                <el-time-picker :placeholder="startTimeTip" class="w-100" v-model="dailies.startTime"
                                value-format="HH:mm:ss"/>
              </div>
              <div class="col-sm-6 col-6">
                <el-time-picker :placeholder="endTimeTip" class="w-100" v-model="dailies.endTime"
                                value-format="HH:mm:ss"/>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12 col-12">
                <el-input :placeholder="contentTip" rows="3" type="textarea" v-model="dailies.content"></el-input>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" data-dismiss="modal" type="button"><i
            class="glyphicon glyphicon-remove"></i>{{closeTip}}
          </button>
          <button @click="this.$parent.remove" class="btn btn-danger" data-dismiss="modal" type="button"><i
            class="glyphicon glyphicon-trash"></i>{{removeTip}}
          </button>
          <button @click="saveDailies" class="btn btn-success" type="button"><i
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
      if (!this.dailies.startTime || !this.dailies.endTime || validator.isEmpty(this.dailies.startTime) || validator.isEmpty(this.dailies.endTime) || validator.isEmpty(this.dailies.content)) {
        utils.showWarning(this, '数据不能为空')
      } else {
        if (validator.isEmpty(this.dailies.id)) {
          requestSaveDailies(this.$parent.daily.id, this.dailies).then(data => this.handleDailiesReturnData(data))
        } else {
          requestUpdateDailies(this.dailies.id, this.dailies).then(data => this.handleDailiesReturnData(data))
        }
      }
    },
    handleDailiesReturnData: function (data) {
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
