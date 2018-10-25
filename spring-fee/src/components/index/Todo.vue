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
          <input type="text" class="border-0 bg-light todo-item w-75" v-model="content" :placeholder="todoTip"/>
        </div>
      </div>
      <!--任务列表-->
      <div v-for="(todo,index) in todos" :key="index" class="row todo" :data-index="index">
        <div class="col-sm-12 col-12" :id="idPrefix+index">
          <div class="pretty p-default p-round p-jelly">
            <input type="checkbox" @click="toggleStatus"/>
            <div class="state p-success-o">
              <label></label>
            </div>
          </div>
          <input type="text" :class="['border-0 bg-light todo-item w-75',todo.status==='1'?'deleted':'']"
                 v-model="todo.content"/>
        </div>
      </div>
      <br/>
    </div>
  </div>
</template>

<script>/* eslint-disable */

export default {
  name: 'Todo',
  data () {
    return {
      todoTip: '添加任务',
      content: '',
      idPrefix: 'todo-item-',
      defaultTodo: {content: '测试', createTime: '', doingDate: '', doneTime: '', id: '', status: '0'},
      todos: []
    }
  },
  props: ['date'],
  methods: {
    toggleStatus: function () {
      let src = window.event.srcElement
      let index = $(src).parents('div.todo').attr('data-index')
      let status = src.checked ? '1' : '0'
      this.todos[index].status = status
    }
  },
  mounted: function () {
    this.todos = [{content: '测试', createTime: '', doingDate: '', doneTime: '', id: '', status: '0'}, {
      content: '测试',
      createTime: '',
      doingDate: '',
      doneTime: '',
      id: '',
      status: '1'
    }]
  },
  watch: {
    date: function (newValue) {
    }
  },
  updated: function () {
    this.todos.forEach((todo, index) => {
      let parent = $(`#${this.idPrefix}${index}`)
      let status = $(parent).find('input[type="checkbox"]')
      let content = $(parent).find('input[type="text"]')
      if (todo.status === '1') {
        $(status).attr('checked', 'checked')
        $(content).attr('disabled', 'disabled')
      } else {
        $(status).removeAttr('checked')
        $(content).removeAttr('disabled')
      }
    })
  }
}
</script>

<!--suppress CssUnusedSymbol -->
<style scoped>
  @import '../../../static/css/pretty-checkbox.min.css';
  @import "../../assets/css/style.css";

  .deleted {
    text-decoration: line-through;
  }

  .todo-item {
    outline: none;
    font-size: 18px;
  }
</style>
