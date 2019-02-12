<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div class="row">
    <div class="col-sm-11 col-10 offset-1 offset-sm-1 rounded bg-light">
      <br/>
      <div class="row">
        <div class="col-sm-2 text-right h5-v-middle" v-if="!isMobile">日期偏移</div>
        <div class="col-sm-3 col-6 text-left">
          <el-input-number :max="365" :min="0" class="w-100" v-model="offset"/>
        </div>
        <div class="col-sm-2 text-right h5-v-middle" v-if="!isMobile">重复次数</div>
        <div class="col-sm-3 col-6 text-left">
          <el-input-number :max="365" :min="0" class="w-100" v-model="repeat"/>
        </div>
      </div>
      <br/>
    </div>
    <div class="col-12 col-sm-12"><br/></div>
    <div class="text-left rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1">
      <br/>
      <!--添加任务-->
      <div class="row">
        <div class="col-sm-12 col-12 border border-top-0 border-left-0 border-right-0 border-bottom"
             style="padding-bottom: 10px;margin-bottom: 10px;">
          <div class="pretty p-default p-round p-locked">
            <input type="checkbox"/>
            <div class="state p-success-o">
              <label></label>
            </div>
          </div>
          <input :placeholder="todoTip" @keyup.enter="saveTodo" class="border-0 bg-light todo-item w-75" type="text"
                 v-model="content"/>
        </div>
      </div>
      <!--任务列表-->
      <todo-item :id-prefix="idPrefix" :todos="todos"></todo-item>
      <br/>
    </div>
    <div class="col-12 col-sm-12"><br/></div>
    <!--未完成的待办事项-->
    <div class="text-left rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1" v-show="undos.length>0">
      <br/>
      <div class="row">
        <div class="col-sm-12 col-12 border border-top-0 border-left-0 border-right-0 border-bottom"
             style="padding-bottom: 10px;margin-bottom: 10px;">
          <h6>{{date+undoTip}}</h6>
        </div>
      </div>
      <todo-item :id-prefix="undoIdPrefix" :todos="undos"></todo-item>
      <br/>
    </div>
    <div class="col-12 col-sm-12"><br/></div>
  </div>
</template>

<script>/* eslint-disable */
import validator from '../../../static/js/validator.min'
import {requestListTodo, requestListUndo, requestSaveTodo} from '../../api/api'
import TodoItem from './todo/TodoItem'
import utils from '../../assets/js/utils'

export default {
  name: 'Todo',
  components: {TodoItem: TodoItem},
  data() {
    return {
      todoTip: '添加任务',
      undoTip: '之前未完成的代办事项',
      content: '',
      idPrefix: 'todo-item-',
      undoIdPrefix: 'undo-item-',
      defaultTodo: {content: '测试', createTime: '', doingDate: '', doneTime: '', id: '', status: '0'},
      todos: [],
      todoLength: 0,
      undos: [],
      undoLength: 0,
      isFirst: true,
      offset: 0,
      repeat: 0,
      isMobile: false
    }
  },
  props: ['date'],
  methods: {
    saveTodo: function () {
      if (validator.isEmpty(this.content)) {
        utils.showWarning(this, '数据不能为空')
      } else {
        requestSaveTodo(this.date, this.content, this.offset, this.repeat).then(data => {
          if (data.code === 200) {
            this.content = ''
            this.repeat = 0
            this.offset = 0
            this.todos.push(data.data)
            utils.showSuccess(this, data.msg)
          } else {
            utils.showError(this, data.msg)
          }
        })
      }
    },
    listTodo: function () {
      this.todos = []
      requestListTodo(this.date).then(data => {
        if (data.code === 200) {
          this.todos = data.data
        } else {
          utils.showError(this, data.msg)
        }
      })
    },
    listUndo: function () {
      this.undos = []
      requestListUndo(this.date).then(data => {
        if (data.code === 200) {
          this.undos = data.data
        } else {
          utils.showError(this, data.msg)
        }
      })
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
    setTimeout(() => {
      if (utils.isNotNull(this.date) && !validator.isEmpty(this.date)) {
        this.listTodo()
        this.listUndo()
        this.isFirst = false
      }
    }, 200)
  },
  watch: {
    date: function () {
      if (!this.isFirst) {
        this.listTodo()
        this.listUndo()
      }
    },
    todos: function () {
      if (this.todos.length !== this.todoLength) {
        this.todoLength = this.todos.length
        this.$parent.getChartData()
      }
    },
    undos: function () {
      if (this.undos.length !== this.undoLength) {
        this.undoLength = this.undos.length
        if (!this.isFirst) {
          this.$parent.getChartData()
        }
      }
    }
  }
}
</script>

<!--suppress CssUnusedSymbol -->
<style scoped>
  @import '../../../static/css/pretty-checkbox.min.css';
  @import "../../assets/css/style.css";

  .todo-item {
    outline: none;
    font-size: 18px;
  }
</style>
