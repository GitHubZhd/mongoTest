package com.springboot.study.batch;

import com.rometools.rome.feed.atom.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by ps on 2017/8/10.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

//    @Bean
//    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
//
//        JobRepositoryFactoryBean jobRepositoryFactoryBean=new JobRepositoryFactoryBean();
//        jobRepositoryFactoryBean.setDataSource(dataSource);
//        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
//        jobRepositoryFactoryBean.setDatabaseType("oracle");
//
//        return jobRepositoryFactoryBean.getObject();
//    }
//
//
//    @Bean
//    public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager){
//        SimpleJobLauncher simpleJobLauncher=new SimpleJobLauncher();
//
//        //simpleJobLauncher.setJobRepository();
//
//        return simpleJobLauncher;
//    }
//
//
//    @Bean
//    public Job importJob(JobBuilderFactory jobBuilderFactory , Step s1){
//
//        return jobBuilderFactory.get("importJob").incrementer(new RunIdIncrementer())
//                .flow(s1).end().build();
//
//    }
//
//    @Bean
//    public Step step(StepBuilderFactory stepBuilderFactory,
//                     ItemReader<Person> reader, ItemWriter<Person> writer,
//                     ItemProcessor<Person,Person> processor){
//
//        return stepBuilderFactory.get("step1").<Person,Person>chunk(65000)
//                .reader(reader).processor(processor).writer(writer).build();
//    }
//
//
//    @Bean
//    public ItemReader<Person> reader(){
//
//    }
}
