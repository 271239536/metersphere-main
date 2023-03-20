package io.metersphere.base.mapper;

import io.metersphere.base.domain.FileModule;
import io.metersphere.base.domain.FileModuleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileModuleMapper {
    long countByExample(FileModuleExample example);

    int deleteByExample(FileModuleExample example);

    int deleteByPrimaryKey(String id);

    int insert(FileModule record);

    int insertSelective(FileModule record);

    List<FileModule> selectByExampleWithBLOBs(FileModuleExample example);

    List<FileModule> selectByExample(FileModuleExample example);

    FileModule selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FileModule record, @Param("example") FileModuleExample example);

    int updateByExampleWithBLOBs(@Param("record") FileModule record, @Param("example") FileModuleExample example);

    int updateByExample(@Param("record") FileModule record, @Param("example") FileModuleExample example);

    int updateByPrimaryKeySelective(FileModule record);

    int updateByPrimaryKeyWithBLOBs(FileModule record);

    int updateByPrimaryKey(FileModule record);
}