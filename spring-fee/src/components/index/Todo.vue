<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div class="row">
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
          <input type="text" class="border-0 bg-light todo-item w-75" v-model="content" :placeholder="todoTip"
                 @keyup.enter="saveTodo"/>
        </div>
      </div>
      <!--任务列表-->
      <todo-template :todos="todos" :id-prefix="idPrefix"></todo-template>
      <br/>
    </div>
    <div class="col-12 col-sm-12"><br/></div>
    <!--未完成的待办事项-->
    <div v-show="undos.length>0" class="text-left rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1">
      <br/>
      <div class="row">
        <div class="col-sm-12 col-12 border border-top-0 border-left-0 border-right-0 border-bottom"
             style="padding-bottom: 10px;margin-bottom: 10px;">
          <h6>{{date+undoTip}}</h6>
        </div>
      </div>
      <todo-template :todos="undos" :id-prefix="undoIdPrefix"></todo-template>
      <br/>
    </div>
  </div>
</template>

<script>/* eslint-disable */
import validator from '../../../static/js/validator.min'
import layer from '../../../static/js/layer'
import {requestListTodo, requestListUndo, requestSaveTodo} from '../../api/api'
import TodoItem from './todo/TodoItem'
import utils from '../../assets/js/utils'

export default {
  name: 'Todo',
  components: {TodoItem: TodoItem},
  data () {
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
      isFirst: true
    }
  },
  props: ['date'],
  methods: {
    saveTodo: function () {
      if (validator.isEmpty(this.content)) {
        layer.alert('数据不能为空')
      } else {
        layer.load(1)
        requestSaveTodo(this.date, this.content).then(data => {
          layer.closeAll()
          if (data.code === 200) {
            this.content = ''
            this.todos.push(data.data)
          } else {
            layer.alert(data.msg)
          }
        })
      }
    },
    listTodo: function () {
      this.todos = []
      layer.load(1)
      requestListTodo(this.date).then(data => {
        layer.closeAll()
        if (data.code === 200) {
          this.todos = data.data
        } else {
          layer.alert(data.msg)
        }
      })
    },
    listUndo: function () {
      this.undos = []
      layer.load(1)
      requestListUndo(this.date).then(data => {
        layer.closeAll()
        if (data.code === 200) {
          this.undos = data.data
        } else {
          layer.alert(data.msg)
        }
      })
    }
  },
  mounted: function () {
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
