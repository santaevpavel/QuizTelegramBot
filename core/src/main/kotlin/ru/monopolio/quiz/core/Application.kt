package ru.monopolio.quiz.core

import ru.monopolio.quiz.core.di.IAppModule
import ru.monopolio.quiz.core.di.IEntityModule
import ru.monopolio.quiz.core.entity.Question
import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.entity.Session

object Application {

    internal val appModule: IAppModule by lazy { appModuleBackingField.checkInit() }
    internal val entityModule: IEntityModule = object : IEntityModule {

        override val round: Round by lazy { Round(appModule.repositories, appModule.scheduler) }

        override val question: Question by lazy { Question() }

        override val session: Session by lazy {
            Session(
                    appModule.repositories,
                    round,
                    question,
                    appModule.scheduler
            )
        }
    }

    private var appModuleBackingField: IAppModule? = null


    fun init(appModule: IAppModule) {
        this.appModuleBackingField = appModule
    }

    private fun <T> T?.checkInit() = this ?: throw IllegalStateException("You must call init before get dependencies")
}