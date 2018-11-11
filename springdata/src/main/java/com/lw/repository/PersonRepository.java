package com.lw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import com.lw.entity.Person;

/**
 * Repository<Person,Integer> 中第一个泛型参数表示需要操作的对象，第二个是主键的类型
 * 不继承接口就使用 @RepositoryDefinition
 * 		@RepositoryDefinition(domainClass=Person.class,idClass=Integer.class)
 * 
 * 注意：beans-springdata中 com.lw配置成com.lw.entity导致扫描不到此Repository出错
 * 	 <jpa:repositories base-package="com.lw"
 * 
 * 选中Repository 然后Ctrl+T可以展示Repository的子类
 * 
 * @author king
 *
 */
@RepositoryDefinition(domainClass=Person.class,idClass=Integer.class)
public interface PersonRepository /*extends Repository<Person,Integer>*/{

	/*
	 * where name = ?
	 */
	public Person findByName(String name);
	
	/*
	 * where name like ?% and age <?
	 * 此方法的命名是有规则的，最终会成为上面的SQL条件
	 */
	public List<Person> findByNameStartingWithAndAgeLessThan(String name,int age);

	/*
	 * where name like %? and age < ?
	 */
	public List<Person> findByNameEndingWithAndAgeLessThan(String name,int age);

	/*
	 * where name in (?,?,?....) or age < ?
	 */
	public List<Person> findByNameInOrAgeLessThan(List<String> names,int age);

	/*
	 * 上面使用命名来规定查询条件肯定不通用，所以需要自定义的查询条件，而和方法名无关
	 * 
	 * 注意此处的是以类名为准，所以Person需要大写P,而表名是person，如果是person那么就错误了。
	 */
	@Query("select p from Person p where pId=(select max(pId) from Person p1)")
	public Person getPersonById();
	
	/*
	 * 1 表示第一个入参
	 * 2 表示第二个入参
	 */
	@Query("select p from Person p where p.name = ?1 and p.age=?2")
	public List<Person> queryByParams(String name,int age);
	
	/*
	 * :name,:age是命名占位符
	 */
	@Query("select p from Person p where p.name = :name and p.age= :age")
	public List<Person> queryByParams02(@Param("name")String name,@Param("age")int age);

	/*
	 * 入参顺序占位符
	 */
	@Query("select p from Person p where p.name like %?1% ")
	public List<Person> queryLikeName(String name);
	
	/*
	 * 命名占位符
	 */
	@Query("select p from Person p where p.name like %:name% ")
	public List<Person> queryLikeName02(@Param("name")String name);
	
	/*
	 * 此处使用原生的SQL查询，所以此处不使用类名，而是使用表名person
	 * nativeQuery=true 表示使用原生SQL
	 */
	@Query(nativeQuery=true,value="select count(1) from person")
	public long getCount();
	
	/*
	 * 更新操作
	 * 需要结合@Query,@Modifying 以及事物控制，事务控制在Service包下 @Transactional
	 */
	@Modifying
	@Query("update Person p set p.age=:age where p.pId=:id")
	public void update(@Param("id")int id,@Param("age")int age);
}
