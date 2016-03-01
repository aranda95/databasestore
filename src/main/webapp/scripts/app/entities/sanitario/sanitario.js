'use strict';

angular.module('databasestoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sanitario', {
                parent: 'entity',
                url: '/sanitarios',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.sanitario.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sanitario/sanitarios.html',
                        controller: 'SanitarioController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sanitario');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sanitario.detail', {
                parent: 'entity',
                url: '/sanitario/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.sanitario.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sanitario/sanitario-detail.html',
                        controller: 'SanitarioDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sanitario');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Sanitario', function($stateParams, Sanitario) {
                        return Sanitario.get({id : $stateParams.id});
                    }]
                }
            })
            .state('sanitario.new', {
                parent: 'sanitario',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/sanitario/sanitario-dialog.html',
                        controller: 'SanitarioDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    modelo: null,
                                    medidas: null,
                                    cantidad: null,
                                    comentario: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('sanitario', null, { reload: true });
                    }, function() {
                        $state.go('sanitario');
                    })
                }]
            })
            .state('sanitario.edit', {
                parent: 'sanitario',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/sanitario/sanitario-dialog.html',
                        controller: 'SanitarioDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Sanitario', function(Sanitario) {
                                return Sanitario.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('sanitario', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('sanitario.delete', {
                parent: 'sanitario',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/sanitario/sanitario-delete-dialog.html',
                        controller: 'SanitarioDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Sanitario', function(Sanitario) {
                                return Sanitario.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('sanitario', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
