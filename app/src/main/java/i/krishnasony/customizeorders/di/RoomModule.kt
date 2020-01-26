package i.krishnasony.customizeorders.di

import androidx.room.Room
import androidx.room.RoomDatabase
import i.krishnasony.customizeorders.room.database.AppDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single{
        Room.databaseBuilder(
            androidApplication(),
            AppDataBase::class.java,
            AppDataBase.DATABASE_NAME
        )   .allowMainThreadQueries()
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }
}
