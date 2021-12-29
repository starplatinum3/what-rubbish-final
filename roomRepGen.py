

# from abc import abstractclassmethod

############# config ############# 
# dao 文件生成在哪里
dao_root_dir=r"D:\什么垃圾\dao"
# repository 文件生成在哪里
repository_root_dir=r"D:\什么垃圾\repository"
# Repository.java 和 AppDatabase.java 的生成目录
gen_root_dir=r"D:\什么垃圾\gen"
# 包名
package_name="com.example.whatrubbish"
# 所有要生成的类名
clsNames="""
Article
Basket
Card
CardGame
City
ColeFragGameNow
ColeFragGameStat
Friendship
Game
GameHonor
GameRecord
Place
Present
PsnExchgRec
Rubbish
RubbishType
RubTyCoresp
ShootGame
SignInHonor
SignInStd
User
WikiHistory"""


############# config ############# 


repository_template="""
package #package_name#.repository;

import #package_name#.db.BaseDao;
import #package_name#.db.BaseRepository;
import #package_name#.entity.#clsName#;

public class #clsName#Repository extends BaseRepository<#clsName#> {

    public #clsName#Repository(BaseDao<#clsName#> baseDao) {
        super(baseDao);
    }

}
"""
repository_define_template="""
  #clsName#Repository #clsNameLow#Repository;
"""
repository_new_template="""
   if (#clsNameLow#Repository == null) {
        #clsNameLow#Repository = new #clsName#Repository(database.#clsNameLow#Dao());
    }
"""
dao_template="""
package #package_name#.dao;

import androidx.room.Dao;
import #package_name#.db.BaseDao;
import #package_name#.entity.#clsName#;

@Dao
public interface #clsName#Dao extends BaseDao<#clsName#> {

}

"""


cls_name_template="#clsName#"
cls_name_mark="#clsName#"
cls_name_low_template="#clsNameLow#"

package_name_template="#package_name#"


import_repository_template="import "+package_name+".repository."+cls_name_mark+"Repository;"


dao_file_template="#clsName#Dao.java"
repository_file_template="#clsName#Repository.java"

def gen_repository_define(cls_name,cls_name_low):
    res=repository_define_template.replace(cls_name_template,cls_name)
    res=res.replace(cls_name_low_template,cls_name_low)
    return  res

# python 第一个字符 变成小写
def gen_repository_new(cls_name,fir_low_cls_name):
    out=repository_new_template.replace(cls_name_template,cls_name)
    out=out.replace(cls_name_low_template,fir_low_cls_name)
    return out

def gen_repository(cls_name,fir_low_cls_name,package_name):
    # out=dao_template.replace(package_name_template,package_name)
    # out=out.replace()
    # package_name_template
    # out=out.replace(cls_name_low_template,fir_low_cls_name)
    
    return replace_marks(repository_template,cls_name,fir_low_cls_name,package_name)


def gen_dao(cls_name,fir_low_cls_name,package_name):
    return replace_marks(dao_template,cls_name,fir_low_cls_name,package_name)


def replace_marks(tplt_str:str,cls_name,fir_low_cls_name,package_name):
    res=tplt_str.replace(cls_name_template,cls_name)
    res=res.replace(cls_name_low_template,fir_low_cls_name)
    res=res.replace(package_name_template,package_name)
    return res

#输入一串字符，将大写字母转换成小写字母，小写字母转换为大写字母
# a = input("请 输入字符：")
# b = []
# for n in a :
#    if "a"<= n <= "z":
#       b.append(n.upper())
#    elif "A" <= n <= "Z":
#       b.append(n.lower())
#    else:
#       b.append(n)
# print("".join(b))
# https://blog.csdn.net/luomanluoland/article/details/80622578

def first_to_lower(string):
    # out=""
    # string[0]
    i=0
    out_arr=[]
    # ch:char
    for ch in string:
        if i==0:
            out_arr.append(ch.lower())
        else:
            out_arr.append(ch)
        i+=1
    return "".join(out_arr)


 


repository_new_res_str=""
repository_define_res_str=""
abstract_dao_res_str=""
# import com.example.whatrubbish.repository.WikiHistoryRepository;
import_res_str=""

import_dao_res_str=""
clss_res_str=""
clss_res_lst=[]
import os

import_entity_res_str=""
lines=clsNames.split("\n")
for line in lines:
    if line=="":
        continue
    clsName=line.strip()
    fir_low_cls_name=first_to_lower(clsName)
    repository_define_one=gen_repository_define(clsName,fir_low_cls_name)

    repository_file_one=gen_repository(clsName,fir_low_cls_name,package_name)
    dao_file_one=gen_dao(clsName,fir_low_cls_name,package_name)
    repository_new_one=gen_repository_new(clsName,fir_low_cls_name)


    repository_new_res_str+=repository_new_one
    repository_define_res_str+=repository_define_one

    # repository_new_res_str+=repository_new_one+"\n"
    # repository_define_res_str+=repository_define_one+"\n"
    abstract_dao_res_str+=f"public abstract {clsName}Dao {fir_low_cls_name}Dao();\n"
    import_res_str+=f"import {package_name}.repository.{clsName}Repository;\n"
    import_dao_res_str+=f"import {package_name}.dao.{clsName}Dao;\n"
    
    import_entity_res_str+=f"import {package_name}.entity.{clsName};\n"
    # clss_res_str+=f"{clsName}.class,"
    clss_res_lst.append(f"{clsName}.class")
    repository_file_name=os.path.join(repository_root_dir,repository_file_template.replace(cls_name_mark,clsName))
    with open(repository_file_name,"w") as f:
        f.write(repository_file_one)

    dao_file_name=os.path.join(dao_root_dir,dao_file_template.replace(cls_name_mark,clsName))
    with open(dao_file_name,"w") as f:
        f.write(dao_file_one)



repository_java_file_content="""
package #pak#.db;

import android.content.Context;
#import#
import lombok.Data;

@Data
public class Repository {

    Context context;
    AppDatabase database;
    #def#

    public Repository(Context context) {
        this.context = context;
        initDatabase(context);
    }

    void initDatabase(Context context) {
        if (database == null) {
            database = AppDatabase.getDatabase(context);
        }
        #new#
    }
}

"""
repository_java_file_content=repository_java_file_content.replace("#import#",import_res_str)
repository_java_file_content=repository_java_file_content.replace("#def#",repository_define_res_str)
repository_java_file_content=repository_java_file_content.replace("#new#",repository_new_res_str)
repository_java_file_content=repository_java_file_content.replace("#pak#",package_name)
repository_java_file_name=os.path.join(gen_root_dir,"Repository.java")
with open(repository_java_file_name,"w") as f:
    f.write(repository_java_file_content)
print("write files repository_java_file_name  "+repository_java_file_name)


appDatabase_java_file_content="""
package #pak#.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
#import_dao#
#import_entity#

@Database(entities = {#clss#}, version = 1, exportSchema = false)
@TypeConverters({RoomConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    static String dbFileName = "whatRubbish.db";
    #def#
   
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, dbFileName)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


"""
clss_res_str=",".join(clss_res_lst)
appDatabase_java_file_content=appDatabase_java_file_content.replace("#import_dao#",import_dao_res_str)
appDatabase_java_file_content=appDatabase_java_file_content.replace("#import_entity#",import_entity_res_str)
appDatabase_java_file_content=appDatabase_java_file_content.replace("#clss#",clss_res_str)
appDatabase_java_file_content=appDatabase_java_file_content.replace("#def#",abstract_dao_res_str)
appDatabase_java_file_content=appDatabase_java_file_content.replace("#pak#",package_name)
appDatabase_java_file_name=os.path.join(gen_root_dir,"AppDatabase.java")
with open(appDatabase_java_file_name,"w") as f:
    f.write(appDatabase_java_file_content)
print("write files appDatabase_java_file_name  "+appDatabase_java_file_name)



print("write files dao_root_dir  " +dao_root_dir)
print("write files repository_root_dir  "+repository_root_dir)

print("========")
print("repository_new\n\n")
print(repository_new_res_str)
print("========\n\n")

print("repository_define_res_str\n\n")
print(repository_define_res_str)
print("========\n\n")


print("abstract_dao_res_str\n\n")
print(abstract_dao_res_str)
print("========\n\n")

print("import_res_str\n\n")
print(import_res_str)
print("========\n\n")
