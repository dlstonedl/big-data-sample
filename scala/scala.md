# Scala

面向对象 + 函数式编程

## 返回值
unit,返回值为void，在scala中表现形式为();     
最后一行代码就是它的返回值；    

## 变量：
var：值可变
val：值不可变，相当于final

## 字符串格式化输出
f: 字符串中可以使用变量，使用printf格式说明符，例如: $name%s,string变量
s: 字符串中可以直接使用变量，如$name，可以使用任意表达式表达式${}

## 运算符重载
运算符实际上是方法

## for循环
to: 1 to 5, [1, 5]  
until: 1 until 5, [1, 5)  
yield
循环过滤: for(变量 <- 表达式/集合/数组; if 守卫)
双层循环: for(i <- 0 to 3; j <- 0 to 3; if i != j)

## 访问修饰符
private（内部可见，比Java严格），protected（子类可见，比Java严格），public；    
作用域保护：private[x]，x代表包，类或单例对象；  

## 继承
trait(特征)：一般情况下，scala的类只能继承单一父亲，但trait可以继承多个；

## 类和对象
scala中的类不声明为public，类的定义可以有参数，称为类参数，类参数在整个类中都可以访问；  

### 继承（extends，单继承）
1. 重写一个非抽象方法必须使用override修饰符；
2. 只有主构函数才可以往基类的构造函数里写参数；
3. 子类重写超类的抽象方法时，不需要使用override关键字；

### 单例对象（Object）
Object对象不能带参数；   
伴生对象与伴生类：当单例对象与某个类共享同一个名称时，必须在同一个源文件里定义类和它的伴生对象；  
伴生类和伴生对象可以相互访问其私有成员；  

## 函数和方法
方法的定义: def 方法名称(参数列表): 方法的返回值类型 = 方法体
def add(a: Int, b: Int): Int = a + b

函数的定义(=>): 
方式一: (函数的参数列表) => 函数体
val add = (x: Int, y: Int) => x + y

方式二: (函数的参数类型列表) => 函数的返回值类型 = (函数的参数变量引用) => 函数体
val add:(Int, Int) => Int = (a, b) => a + b
val prtf:String => Unit = msg => println(msg) 

方法可以转换成函数: 方法名称 _

函数作为参数传递:
def add(f:(Int, Int) => Int, a: Int, b: Int) = {
  f(a, b)
}

传名调用和传值调用: add(f, 2 + 8, 6)

### 偏应用函数
```
//原函数
def log(date: Date, message: String) = println(date + " " + message)
//偏应用函数
val date = new Date
val logWithDate = log(date, _ : String)
```

### 函数柯里化（currying）
```
def add(x: Int, y: Int) = x + y
//柯里化
def add(x: Int)(y: Int) = x + y
//等价于
def add(x: Int) = (y: Int) => x +y
```
 

